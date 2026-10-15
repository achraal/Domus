package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.PeriodeRapport;
import com.achraf.domusproject.enums.TypeRapport;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class RapportDTO {
    private Long idRapport;
    private TypeRapport typeRapport;
    private PeriodeRapport periode;
    private LocalDateTime dateGeneration;
    private String contenu;
}