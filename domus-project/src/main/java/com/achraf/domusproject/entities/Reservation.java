package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce", nullable = false)
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_locataire", nullable = false)
    private Utilisateur locataire;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    private Integer nombreDePersonnes;

    @Column(nullable = false)
    private BigDecimal prixTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;
}