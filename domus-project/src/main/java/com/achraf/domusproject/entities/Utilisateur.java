package com.achraf.domusproject.entities;

import com.achraf.domusproject.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "utilisateurs")
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_utilisateur", nullable = false)
    private Role typeUtilisateur;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    private String telephone;
    private String adresse;
}
