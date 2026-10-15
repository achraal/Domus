package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.EtatType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class EtatAppartementDTO {
    private Long idEtat;
    private Long idAnnonce;
    private LocalDate dateEtat;
    private EtatType etat;
    private String commentaire;
}