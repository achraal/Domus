package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.TarificationDTO;
import com.achraf.domusproject.entities.Annonce;
import com.achraf.domusproject.entities.TarificationSaisonniere;
import com.achraf.domusproject.repository.AnnonceRepository;
import com.achraf.domusproject.repository.TarificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarificationServiceImpl implements TarificationService {

    private final TarificationRepository tarificationRepository;
    private final AnnonceRepository annonceRepository;

    @Override
    public TarificationDTO ajouterTarification(TarificationDTO dto) {
        Annonce annonce = annonceRepository.findById(dto.getIdAnnonce())
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        TarificationSaisonniere tarif = TarificationSaisonniere.builder()
                .annonce(annonce)
                .saison(dto.getSaison())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .prixParNuit(dto.getPrixParNuit())
                .promotion(dto.getPromotion() != null ? dto.getPromotion() : BigDecimal.ZERO)
                .build();

        return mapToDTO(tarificationRepository.save(tarif));
    }

    @Override
    public List<TarificationDTO> obtenirTarifications(Long idAnnonce) {
        return tarificationRepository.findByAnnonceIdAnnonce(idAnnonce)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void supprimerTarification(Long idTarification) {
        tarificationRepository.deleteById(idTarification);
    }

    @Override
    public BigDecimal calculerPrixPourDate(Long idAnnonce, LocalDate date) {
        Optional<TarificationSaisonniere> tarifSaison = tarificationRepository.findActiveTarification(idAnnonce, date);

        if (tarifSaison.isPresent()) {
            TarificationSaisonniere ts = tarifSaison.get();
            BigDecimal prix = ts.getPrixParNuit();
            // Appliquer la promotion si elle existe
            if (ts.getPromotion() != null && ts.getPromotion().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal reduction = prix.multiply(ts.getPromotion()).divide(new BigDecimal("100"));
                prix = prix.subtract(reduction);
            }
            return prix;
        }

        // Retourner le prix de base de l'annonce si aucune tarification dynamique n'est active
        Annonce annonce = annonceRepository.findById(idAnnonce)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));
        return annonce.getPrixParNuitBase();
    }

    private TarificationDTO mapToDTO(TarificationSaisonniere tarif) {
        return TarificationDTO.builder()
                .idTarification(tarif.getIdTarification())
                .idAnnonce(tarif.getAnnonce().getIdAnnonce())
                .saison(tarif.getSaison())
                .dateDebut(tarif.getDateDebut())
                .dateFin(tarif.getDateFin())
                .prixParNuit(tarif.getPrixParNuit())
                .promotion(tarif.getPromotion())
                .build();
    }
}