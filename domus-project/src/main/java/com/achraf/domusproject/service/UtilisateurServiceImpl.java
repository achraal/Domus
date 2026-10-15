package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.UtilisateurRequestDTO;
import com.achraf.domusproject.dto.UtilisateurResponseDTO;
import com.achraf.domusproject.entities.Utilisateur;
import com.achraf.domusproject.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UtilisateurResponseDTO creerCompte(UtilisateurRequestDTO request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("L'email est déjà utilisé");
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .typeUtilisateur(request.getTypeUtilisateur())
                .build();

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return mapToDTO(saved);
    }

    @Override
    public UtilisateurResponseDTO obtenirParId(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return mapToDTO(utilisateur);
    }

    @Override
    public List<UtilisateurResponseDTO> obtenirTous() {
        return utilisateurRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurResponseDTO modifierCompte(Long id, UtilisateurRequestDTO request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setAdresse(request.getAdresse());

        if (request.getMotDePasse() != null && !request.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }

        return mapToDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void supprimerCompte(Long id) {
        utilisateurRepository.deleteById(id);
    }

    private UtilisateurResponseDTO mapToDTO(Utilisateur utilisateur) {
        return UtilisateurResponseDTO.builder()
                .idUtilisateur(utilisateur.getIdUtilisateur())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .adresse(utilisateur.getAdresse())
                .typeUtilisateur(utilisateur.getTypeUtilisateur())
                .build();
    }
}