package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.EtatType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AnnonceRequestDTO {
    private String titre;
    private String description;
    private String adresse;
    private String ville;
    private String codePostal;
    private Integer nombreDeChambres;
    private Double superficie;
    private BigDecimal prixParNuit;
    private List<String> photos;
    private String caracteristiques;
    private EtatType etat;
}