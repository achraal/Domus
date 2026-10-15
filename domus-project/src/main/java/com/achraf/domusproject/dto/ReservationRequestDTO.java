package com.achraf.domusproject.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationRequestDTO {
    private Long idAnnonce;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer nombreDePersonnes;
}