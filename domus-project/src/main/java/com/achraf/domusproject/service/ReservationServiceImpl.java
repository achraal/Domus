package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.ReservationRequestDTO;
import com.achraf.domusproject.dto.ReservationResponseDTO;
import com.achraf.domusproject.entities.Annonce;
import com.achraf.domusproject.entities.Reservation;
import com.achraf.domusproject.entities.Utilisateur;
import com.achraf.domusproject.enums.EtatType;
import com.achraf.domusproject.enums.Role;
import com.achraf.domusproject.enums.StatutReservation;
import com.achraf.domusproject.repository.AnnonceRepository;
import com.achraf.domusproject.repository.ReservationRepository;
import com.achraf.domusproject.repository.UtilisateurRepository;
import com.achraf.domusproject.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final AnnonceRepository annonceRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public ReservationResponseDTO creerReservation(ReservationRequestDTO request, String emailLocataire) {
        Annonce annonce = annonceRepository.findById(request.getIdAnnonce())
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        if(annonce.getEtat() != EtatType.LIBRE) {
            throw new RuntimeException("L'appartement n'est pas disponible pour le moment");
        }

        // Vérification des conflits de dates
        long conflits = reservationRepository.countConflictingReservations(
                request.getIdAnnonce(), request.getDateDebut(), request.getDateFin()
        );

        if (conflits > 0) {
            throw new RuntimeException("Ces dates sont déjà réservées pour cette annonce");
        }

        Utilisateur locataire = utilisateurRepository.findByEmail(emailLocataire)
                .orElseThrow(() -> new RuntimeException("Locataire introuvable"));

        // Calcul du prix total
        long jours = ChronoUnit.DAYS.between(request.getDateDebut(), request.getDateFin());
        if(jours <= 0) throw new RuntimeException("La date de fin doit être après la date de début");

        BigDecimal prixTotal = annonce.getPrixParNuit().multiply(BigDecimal.valueOf(jours));

        Reservation reservation = Reservation.builder()
                .annonce(annonce)
                .locataire(locataire)
                .dateDebut(request.getDateDebut())
                .dateFin(request.getDateFin())
                .nombreDePersonnes(request.getNombreDePersonnes())
                .prixTotal(prixTotal)
                .statut(StatutReservation.EN_ATTENTE)
                .build();

        return mapToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponseDTO changerStatutReservation(Long idReservation, StatutReservation nouveauStatut, String emailUtilisateur) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        Utilisateur utilisateur = utilisateurRepository.findByEmail(emailUtilisateur).orElseThrow();

        // Seul le propriétaire ou un admin peut valider/refuser
        if (utilisateur.getTypeUtilisateur() == Role.PROPRIETAIRE &&
                !reservation.getAnnonce().getProprietaire().getIdUtilisateur().equals(utilisateur.getIdUtilisateur())) {
            throw new RuntimeException("Non autorisé");
        }

        reservation.setStatut(nouveauStatut);

        // Mettre à jour l'état de l'appartement si confirmée
        if (nouveauStatut == StatutReservation.CONFIRMEE) {
            reservation.getAnnonce().setEtat(EtatType.OCCUPE);
            annonceRepository.save(reservation.getAnnonce());
        }

        return mapToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public void annulerReservation(Long idReservation, String emailUtilisateur) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        Utilisateur utilisateur = utilisateurRepository.findByEmail(emailUtilisateur).orElseThrow();

        // Le locataire peut annuler sa propre réservation
        if (utilisateur.getTypeUtilisateur() == Role.LOCATAIRE &&
                !reservation.getLocataire().getIdUtilisateur().equals(utilisateur.getIdUtilisateur())) {
            throw new RuntimeException("Non autorisé à annuler cette réservation");
        }

        reservation.setStatut(StatutReservation.ANNULEE);
        reservationRepository.save(reservation);
    }

    @Override
    public ReservationResponseDTO obtenirParId(Long id) {
        return mapToDTO(reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable")));
    }

    @Override
    public List<ReservationResponseDTO> obtenirReservationsParLocataire(String emailLocataire) {
        Utilisateur locataire = utilisateurRepository.findByEmail(emailLocataire).orElseThrow();
        return reservationRepository.findByLocataireIdUtilisateur(locataire.getIdUtilisateur()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDTO> obtenirReservationsParAnnonce(Long idAnnonce) {
        return reservationRepository.findByAnnonceIdAnnonce(idAnnonce).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReservationResponseDTO mapToDTO(Reservation reservation) {
        return ReservationResponseDTO.builder()
                .idReservation(reservation.getIdReservation())
                .idAnnonce(reservation.getAnnonce().getIdAnnonce())
                .titreAnnonce(reservation.getAnnonce().getTitre())
                .idLocataire(reservation.getLocataire().getIdUtilisateur())
                .nomLocataire(reservation.getLocataire().getNom() + " " + reservation.getLocataire().getPrenom())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .nombreDePersonnes(reservation.getNombreDePersonnes())
                .prixTotal(reservation.getPrixTotal())
                .statut(reservation.getStatut())
                .build();
    }
}