package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.UtilisateurRequestDTO;
import com.achraf.domusproject.dto.UtilisateurResponseDTO;
import java.util.List;

public interface UtilisateurService {
    UtilisateurResponseDTO creerCompte(UtilisateurRequestDTO requestDTO);
    UtilisateurResponseDTO obtenirParId(Long id);
    List<UtilisateurResponseDTO> obtenirTous();
    UtilisateurResponseDTO modifierCompte(Long id, UtilisateurRequestDTO requestDTO);
    void supprimerCompte(Long id);
}