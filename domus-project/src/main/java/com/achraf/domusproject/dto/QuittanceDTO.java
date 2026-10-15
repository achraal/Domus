package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.StatutQuittance;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class QuittanceDTO {
    private Long idQuittance;
    private Long idPaiement;
    private LocalDate dateEmission;
    private BigDecimal montant;
    private StatutQuittance statut;
}