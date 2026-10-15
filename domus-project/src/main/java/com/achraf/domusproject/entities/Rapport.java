package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.PeriodeRapport;
import com.achraf.domusproject.enums.TypeRapport;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rapports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRapport;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_rapport")
    private TypeRapport typeRapport;

    @Enumerated(EnumType.STRING)
    private PeriodeRapport periode;

    @Column(name = "date_generation")
    private LocalDateTime dateGeneration;

    @Column(columnDefinition = "TEXT")
    private String contenu; // Stockage JSON ou CSV
}