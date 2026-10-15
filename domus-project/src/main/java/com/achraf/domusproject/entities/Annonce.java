package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.EtatType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "annonces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnnonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proprietaire", nullable = false)
    private Utilisateur proprietaire;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String adresse;
    private String ville;
    private String codePostal;
    private Integer nombreDeChambres;
    private Double superficie;

    @Column(nullable = false)
    private BigDecimal prixParNuit;

    @ElementCollection
    private List<String> photos;

    @Column(columnDefinition = "TEXT")
    private String caracteristiques;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatType etat = EtatType.LIBRE;

    @Column(name = "prix_par_nuit")
    private BigDecimal prixParNuitBase;
}