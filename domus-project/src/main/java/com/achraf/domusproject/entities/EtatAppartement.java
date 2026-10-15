package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.EtatType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "etats_appartements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtatAppartement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce", nullable = false)
    private Annonce annonce;

    @Column(name = "date_etat", nullable = false)
    private LocalDate dateEtat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatType etat;

    @Column(columnDefinition = "TEXT")
    private String commentaire;
}