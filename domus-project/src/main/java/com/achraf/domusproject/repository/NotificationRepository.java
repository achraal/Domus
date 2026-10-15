package com.achraf.domusproject.repository;

import com.achraf.domusproject.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUtilisateurIdUtilisateurOrderByDateCreationDesc(Long idUtilisateur);
}