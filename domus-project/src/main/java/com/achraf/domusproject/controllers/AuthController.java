package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.AuthRequestDTO;
import com.achraf.domusproject.dto.AuthResponseDTO;
import com.achraf.domusproject.dto.UtilisateurRequestDTO;
import com.achraf.domusproject.dto.UtilisateurResponseDTO;
import com.achraf.domusproject.service.AuthService;
import com.achraf.domusproject.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UtilisateurService utilisateurService;

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurResponseDTO> inscription(@RequestBody UtilisateurRequestDTO request) {
        return ResponseEntity.ok(utilisateurService.creerCompte(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authentifier(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authentifier(request));
    }
}