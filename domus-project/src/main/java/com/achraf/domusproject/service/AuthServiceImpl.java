package com.achraf.domusproject.service;

import com.achraf.domusproject.config.JwtService;
import com.achraf.domusproject.dto.AuthRequestDTO;
import com.achraf.domusproject.dto.AuthResponseDTO;
import com.achraf.domusproject.dto.UtilisateurResponseDTO;
import com.achraf.domusproject.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO authentifier(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );
        var utilisateur = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        var jwtToken = jwtService.generateToken(utilisateur);

        UtilisateurResponseDTO userDto = UtilisateurResponseDTO.builder()
                .idUtilisateur(utilisateur.getIdUtilisateur())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .typeUtilisateur(utilisateur.getTypeUtilisateur())
                .build();

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .utilisateur(userDto)
                .build();
    }
}