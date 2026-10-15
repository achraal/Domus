package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.EtatAppartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtatAppartementRepository extends JpaRepository<EtatAppartement, Long> {
    // Pour récupérer l'historique par ordre chronologique décroissant
    List<EtatAppartement> findByAnnonceIdAnnonceOrderByDateEtatDesc(Long idAnnonce);
}