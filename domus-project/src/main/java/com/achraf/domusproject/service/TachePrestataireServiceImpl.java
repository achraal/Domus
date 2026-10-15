package com.achraf.domusproject.service.impl;

import com.achraf.domusproject.dto.TachePrestataireDTO;
import com.achraf.domusproject.entities.*;
import com.achraf.domusproject.repository.*;
import com.achraf.domusproject.service.TachePrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TachePrestataireServiceImpl implements TachePrestataireService {

    private final TachePrestataireRepository tacheRepo;
    private final AnnonceRepository annonceRepo;
    private final UtilisateurRepository userRepo;

    @Override
    public TachePrestataireDTO assignerTache(TachePrestataireDTO dto) {
        Annonce annonce = annonceRepo.findById(dto.getIdAnnonce()).orElseThrow();
        Utilisateur prestataire = userRepo.findById(dto.getIdPrestataire()).orElseThrow();

        TachePrestataire tache = TachePrestataire.builder()
                .annonce(annonce).prestataire(prestataire)
                .description(dto.getDescription())
                .datePlanifiee(dto.getDatePlanifiee())
                .statut(com.achraf.domusproject.enums.StatutTache.PLANIFIEE).build();

        return mapDTO(tacheRepo.save(tache));
    }

    @Override
    public TachePrestataireDTO mettreAJourStatut(Long idTache, TachePrestataireDTO dto) {
        TachePrestataire tache = tacheRepo.findById(idTache).orElseThrow();
        tache.setStatut(dto.getStatut());
        if(dto.getStatut() == com.achraf.domusproject.enums.StatutTache.TERMINEE) {
            tache.setDateExecution(dto.getDateExecution() != null ? dto.getDateExecution() : java.time.LocalDate.now());
        }
        return mapDTO(tacheRepo.save(tache));
    }

    @Override
    public List<TachePrestataireDTO> obtenirTachesParPrestataire(Long idPrestataire) {
        return tacheRepo.findByPrestataireIdUtilisateur(idPrestataire)
                .stream().map(this::mapDTO).collect(Collectors.toList());
    }

    private TachePrestataireDTO mapDTO(TachePrestataire t) {
        return TachePrestataireDTO.builder().idTache(t.getIdTache())
                .idAnnonce(t.getAnnonce().getIdAnnonce())
                .idPrestataire(t.getPrestataire().getIdUtilisateur())
                .description(t.getDescription())
                .datePlanifiee(t.getDatePlanifiee())
                .dateExecution(t.getDateExecution())
                .statut(t.getStatut()).build();
    }
}