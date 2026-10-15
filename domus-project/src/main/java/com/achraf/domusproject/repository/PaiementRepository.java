package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.Paiement;
import com.achraf.domusproject.enums.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByReservationIdReservation(Long idReservation);
    Optional<Paiement> findByReservationIdReservationAndTypeTransaction(Long idReservation, TypeTransaction type);
}