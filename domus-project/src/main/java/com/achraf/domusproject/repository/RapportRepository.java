package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.*;
import com.achraf.domusproject.enums.TypeRapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {
    List<Rapport> findByTypeRapport(TypeRapport type);
}