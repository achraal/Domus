package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.StatutReservation;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ReservationResponseDTO {
    private Long idReservation;
    private Long idAnnonce;
    private String titreAnnonce;
    private Long idLocataire;
    private String nomLocataire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer nombreDePersonnes;
    private BigDecimal prixTotal;
    private StatutReservation statut;
}