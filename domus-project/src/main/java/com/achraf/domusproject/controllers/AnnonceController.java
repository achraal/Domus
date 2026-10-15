package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.AnnonceRequestDTO;
import com.achraf.domusproject.dto.AnnonceResponseDTO;
import com.achraf.domusproject.service.AnnonceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@RequiredArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;

    @PostMapping
    @PreAuthorize("hasRole('PROPRIETAIRE')")
    public ResponseEntity<AnnonceResponseDTO> creerAnnonce(@RequestBody AnnonceRequestDTO request, Authentication authentication) {
        return ResponseEntity.ok(annonceService.creerAnnonce(request, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROPRIETAIRE')")
    public ResponseEntity<AnnonceResponseDTO> modifierAnnonce(@PathVariable Long id, @RequestBody AnnonceRequestDTO request, Authentication authentication) {
        return ResponseEntity.ok(annonceService.modifierAnnonce(id, request, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROPRIETAIRE') or hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> supprimerAnnonce(@PathVariable Long id, Authentication authentication) {
        annonceService.supprimerAnnonce(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AnnonceResponseDTO>> obtenirToutesAnnonces() {
        return ResponseEntity.ok(annonceService.obtenirToutesAnnoncesDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> obtenirAnnonceParId(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.obtenirParId(id));
    }

    @GetMapping("/mes-annonces")
    @PreAuthorize("hasRole('PROPRIETAIRE')")
    public ResponseEntity<List<AnnonceResponseDTO>> obtenirMesAnnonces(Authentication authentication) {
        return ResponseEntity.ok(annonceService.obtenirAnnoncesParProprietaire(authentication.getName()));
    }
}