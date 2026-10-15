package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.EtatAppartementDTO;
import com.achraf.domusproject.entities.Annonce;
import com.achraf.domusproject.entities.EtatAppartement;
import com.achraf.domusproject.repository.AnnonceRepository;
import com.achraf.domusproject.repository.EtatAppartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtatAppartementServiceImpl implements EtatAppartementService {

    private final EtatAppartementRepository etatRepository;
    private final AnnonceRepository annonceRepository; // Assumé créé pour le module 3.1

    @Override
    public EtatAppartementDTO mettreAJourEtat(EtatAppartementDTO dto) {
        Annonce annonce = annonceRepository.findById(dto.getIdAnnonce())
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        EtatAppartement etat = EtatAppartement.builder()
                .annonce(annonce)
                .dateEtat(dto.getDateEtat() != null ? dto.getDateEtat() : LocalDate.now())
                .etat(dto.getEtat())
                .commentaire(dto.getCommentaire())
                .build();

        return mapToDTO(etatRepository.save(etat));
    }

    @Override
    public List<EtatAppartementDTO> obtenirHistorique(Long idAnnonce) {
        return etatRepository.findByAnnonceIdAnnonceOrderByDateEtatDesc(idAnnonce)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EtatAppartementDTO obtenirEtatActuel(Long idAnnonce) {
        List<EtatAppartement> historique = etatRepository.findByAnnonceIdAnnonceOrderByDateEtatDesc(idAnnonce);
        if (historique.isEmpty()) {
            throw new RuntimeException("Aucun état enregistré pour cette annonce");
        }
        return mapToDTO(historique.get(0));
    }

    private EtatAppartementDTO mapToDTO(EtatAppartement etat) {
        return EtatAppartementDTO.builder()
                .idEtat(etat.getIdEtat())
                .idAnnonce(etat.getAnnonce().getIdAnnonce())
                .dateEtat(etat.getDateEtat())
                .etat(etat.getEtat())
                .commentaire(etat.getCommentaire())
                .build();
    }
}