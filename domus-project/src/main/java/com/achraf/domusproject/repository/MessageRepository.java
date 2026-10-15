package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByExpediteurIdUtilisateurOrDestinataireIdUtilisateur(Long expId, Long destId);
}