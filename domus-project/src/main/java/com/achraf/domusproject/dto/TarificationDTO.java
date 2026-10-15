package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.Saison;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TarificationDTO {
    private Long idTarification;
    private Long idAnnonce;
    private Saison saison;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal prixParNuit;
    private BigDecimal promotion;
}