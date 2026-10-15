package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private LocalDateTime dateCreation;
    private boolean lue;
}