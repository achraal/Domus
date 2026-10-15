package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.MethodePaiement;
import com.achraf.domusproject.enums.StatutPaiement;
import com.achraf.domusproject.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @Column(nullable = false)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "methode_paiement")
    private MethodePaiement methodePaiement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false)
    private TypeTransaction typeTransaction;
}