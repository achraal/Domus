package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.AnnonceRequestDTO;
import com.achraf.domusproject.dto.AnnonceResponseDTO;
import java.util.List;

public interface AnnonceService {
    AnnonceResponseDTO creerAnnonce(AnnonceRequestDTO request, String emailProprietaire);
    AnnonceResponseDTO modifierAnnonce(Long id, AnnonceRequestDTO request, String emailProprietaire);
    void supprimerAnnonce(Long id, String emailProprietaire);
    AnnonceResponseDTO obtenirParId(Long id);
    List<AnnonceResponseDTO> obtenirToutesAnnoncesDisponibles();
    List<AnnonceResponseDTO> obtenirAnnoncesParProprietaire(String emailProprietaire);
}