package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.Saison;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarification_saisonniere")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarificationSaisonniere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce", nullable = false)
    private Annonce annonce;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Saison saison;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "prix_par_nuit", nullable = false)
    private BigDecimal prixParNuit;

    @Column(precision = 5, scale = 2)
    private BigDecimal promotion; // Pourcentage de réduction
}