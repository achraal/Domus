package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.TarificationSaisonniere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarificationRepository extends JpaRepository<TarificationSaisonniere, Long> {
    List<TarificationSaisonniere> findByAnnonceIdAnnonce(Long idAnnonce);

    // Trouver la tarification active pour une date donnée
    @Query("SELECT t FROM TarificationSaisonniere t WHERE t.annonce.idAnnonce = :idAnnonce " +
            "AND :date BETWEEN t.dateDebut AND t.dateFin")
    Optional<TarificationSaisonniere> findActiveTarification(@Param("idAnnonce") Long idAnnonce, @Param("date") LocalDate date);
}