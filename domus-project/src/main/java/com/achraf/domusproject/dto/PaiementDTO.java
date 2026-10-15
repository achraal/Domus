package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.MethodePaiement;
import com.achraf.domusproject.enums.StatutPaiement;
import com.achraf.domusproject.enums.TypeTransaction;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaiementDTO {
    private Long idPaiement;
    private Long idReservation;
    private LocalDateTime datePaiement;
    private BigDecimal montant;
    private MethodePaiement methodePaiement;
    private StatutPaiement statut;
    private TypeTransaction typeTransaction;
}