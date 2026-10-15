package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.Annonce;
import com.achraf.domusproject.enums.EtatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByProprietaireIdUtilisateur(Long idProprietaire);
    List<Annonce> findByEtat(EtatType etat);
}