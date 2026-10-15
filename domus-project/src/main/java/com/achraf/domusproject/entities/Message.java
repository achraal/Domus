package com.achraf.domusproject.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediteur", nullable = false)
    private Utilisateur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_destinataire", nullable = false)
    private Utilisateur destinataire;

    @Column(name = "date_message", nullable = false)
    private LocalDateTime dateMessage;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;
}