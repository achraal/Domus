package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilisateurResponseDTO {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private Role typeUtilisateur;
}
