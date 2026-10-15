package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TachePrestataireRepository extends JpaRepository<TachePrestataire, Long> {
    List<TachePrestataire> findByPrestataireIdUtilisateur(Long idPrestataire);
    List<TachePrestataire> findByAnnonceIdAnnonce(Long idAnnonce);
}