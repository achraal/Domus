package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.Quittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuittanceRepository extends JpaRepository<Quittance, Long> {
    Optional<Quittance> findByPaiementIdPaiement(Long idPaiement);
}