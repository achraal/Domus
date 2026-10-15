package com.achraf.domusproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private String token;
    private UtilisateurResponseDTO utilisateur;
}