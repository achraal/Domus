package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.PaiementDTO;
import com.achraf.domusproject.dto.QuittanceDTO;
import com.achraf.domusproject.entities.Paiement;
import com.achraf.domusproject.entities.Quittance;
import com.achraf.domusproject.entities.Reservation;
import com.achraf.domusproject.enums.StatutPaiement;
import com.achraf.domusproject.enums.StatutQuittance;
import com.achraf.domusproject.enums.TypeTransaction;
import com.achraf.domusproject.repository.PaiementRepository;
import com.achraf.domusproject.repository.QuittanceRepository;
import com.achraf.domusproject.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final QuittanceRepository quittanceRepository;
    private final ReservationRepository reservationRepository; // À créer dans le module Réservations

    @Override
    @Transactional
    public PaiementDTO traiterPaiement(PaiementDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getIdReservation())
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        // Simulation d'une intégration avec une API de paiement (ex: Stripe)
        StatutPaiement statutFinal = StatutPaiement.REUSSI; // On assume le succès pour l'exemple[cite: 1]

        Paiement paiement = Paiement.builder()
                .reservation(reservation)
                .datePaiement(LocalDateTime.now())
                .montant(dto.getMontant())
                .methodePaiement(dto.getMethodePaiement())
                .statut(statutFinal)
                .typeTransaction(dto.getTypeTransaction() != null ? dto.getTypeTransaction() : TypeTransaction.LOYER)
                .build();

        paiement = paiementRepository.save(paiement);

        // Génération automatique de quittance si le paiement est réussi[cite: 1]
        if (statutFinal == StatutPaiement.REUSSI && paiement.getTypeTransaction() == TypeTransaction.LOYER) {
            genererQuittance(paiement.getIdPaiement());
        }

        return mapPaiementDTO(paiement);
    }

    @Override
    public List<PaiementDTO> obtenirPaiementsParReservation(Long idReservation) {
        return paiementRepository.findByReservationIdReservation(idReservation)
                .stream().map(this::mapPaiementDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuittanceDTO genererQuittance(Long idPaiement) {
        Paiement paiement = paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));

        if (paiement.getStatut() != StatutPaiement.REUSSI) {
            throw new RuntimeException("Impossible de générer une quittance pour un paiement non abouti");
        }

        Quittance quittance = Quittance.builder()
                .paiement(paiement)
                .dateEmission(LocalDate.now())
                .montant(paiement.getMontant())
                .statut(StatutQuittance.ENVOYEE) // Statut par défaut selon[cite: 2]
                .build();

        return mapQuittanceDTO(quittanceRepository.save(quittance));
    }

    @Override
    public QuittanceDTO obtenirQuittanceParPaiement(Long idPaiement) {
        Quittance quittance = quittanceRepository.findByPaiementIdPaiement(idPaiement)
                .orElseThrow(() -> new RuntimeException("Quittance introuvable pour ce paiement"));
        return mapQuittanceDTO(quittance);
    }

    @Override
    @Transactional
    public PaiementDTO rembourserDepotGarantie(Long idReservation) {
        Paiement depot = paiementRepository.findByReservationIdReservationAndTypeTransaction(idReservation, TypeTransaction.DEPOT_GARANTIE)
                .orElseThrow(() -> new RuntimeException("Aucun dépôt de garantie trouvé pour cette réservation"));

        if (depot.getStatut() == StatutPaiement.REMBOURSE) {
            throw new RuntimeException("Ce dépôt a déjà été remboursé");
        }

        // Modification du statut existant ou création d'une nouvelle transaction de remboursement[cite: 1]
        depot.setStatut(StatutPaiement.REMBOURSE);
        return mapPaiementDTO(paiementRepository.save(depot));
    }

    private PaiementDTO mapPaiementDTO(Paiement p) {
        return PaiementDTO.builder()
                .idPaiement(p.getIdPaiement())
                .idReservation(p.getReservation().getIdReservation())
                .datePaiement(p.getDatePaiement())
                .montant(p.getMontant())
                .methodePaiement(p.getMethodePaiement())
                .statut(p.getStatut())
                .typeTransaction(p.getTypeTransaction())
                .build();
    }

    private QuittanceDTO mapQuittanceDTO(Quittance q) {
        return QuittanceDTO.builder()
                .idQuittance(q.getIdQuittance())
                .idPaiement(q.getPaiement().getIdPaiement())
                .dateEmission(q.getDateEmission())
                .montant(q.getMontant())
                .statut(q.getStatut())
                .build();
    }
}