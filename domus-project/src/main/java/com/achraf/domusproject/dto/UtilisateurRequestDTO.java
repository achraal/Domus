package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.Role;
import lombok.Data;

@Data
public class UtilisateurRequestDTO {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String adresse;
    private Role typeUtilisateur;
}