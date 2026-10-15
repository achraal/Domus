package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.AuthRequestDTO;
import com.achraf.domusproject.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO authentifier(AuthRequestDTO request);
}