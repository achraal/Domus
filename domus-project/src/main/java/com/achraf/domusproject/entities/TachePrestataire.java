package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.StatutTache;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "taches_prestataires")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TachePrestataire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce", nullable = false)
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prestataire", nullable = false)
    private Utilisateur prestataire;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_planifiee")
    private LocalDate datePlanifiee;

    @Column(name = "date_execution")
    private LocalDate dateExecution;

    @Enumerated(EnumType.STRING)
    private StatutTache statut;
}