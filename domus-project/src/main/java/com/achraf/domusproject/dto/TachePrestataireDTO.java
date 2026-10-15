package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.StatutTache;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class TachePrestataireDTO {
    private Long idTache;
    private Long idAnnonce;
    private Long idPrestataire;
    private String description;
    private LocalDate datePlanifiee;
    private LocalDate dateExecution;
    private StatutTache statut;
}