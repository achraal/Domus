package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.StatutQuittance;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "quittances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quittance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuittance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paiement", nullable = false)
    private Paiement paiement;

    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;

    @Column(nullable = false)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutQuittance statut;
}