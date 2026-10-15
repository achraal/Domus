package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.UtilisateurRequestDTO;
import com.achraf.domusproject.dto.UtilisateurResponseDTO;
import com.achraf.domusproject.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<List<UtilisateurResponseDTO>> obtenirTous() {
        return ResponseEntity.ok(utilisateurService.obtenirTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDTO> obtenirParId(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.obtenirParId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDTO> modifierCompte(
            @PathVariable Long id,
            @RequestBody UtilisateurRequestDTO request) {
        return ResponseEntity.ok(utilisateurService.modifierCompte(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> supprimerCompte(@PathVariable Long id) {
        utilisateurService.supprimerCompte(id);
        return ResponseEntity.noContent().build();
    }
}