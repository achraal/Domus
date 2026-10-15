package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByLocataireIdUtilisateur(Long idLocataire);
    List<Reservation> findByAnnonceIdAnnonce(Long idAnnonce);

    // Requête pour vérifier les conflits de dates pour une annonce spécifique
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.annonce.idAnnonce = :idAnnonce " +
            "AND r.statut != 'ANNULEE' " +
            "AND (r.dateDebut < :dateFin AND r.dateFin > :dateDebut)")
    long countConflictingReservations(@Param("idAnnonce") Long idAnnonce,
                                      @Param("dateDebut") LocalDate dateDebut,
                                      @Param("dateFin") LocalDate dateFin);
}
