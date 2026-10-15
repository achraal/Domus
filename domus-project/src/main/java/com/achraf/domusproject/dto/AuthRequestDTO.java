package com.achraf.domusproject.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String motDePasse;
}