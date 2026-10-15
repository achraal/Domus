package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.*;
import com.achraf.domusproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
public class TachePrestataireController {
    private final TachePrestataireService tacheService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<TachePrestataireDTO> assignerTache(@RequestBody TachePrestataireDTO dto) {
        return ResponseEntity.ok(tacheService.assignerTache(dto));
    }

    @PutMapping("/{idTache}/statut")
    @PreAuthorize("hasAnyRole('PRESTATAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<TachePrestataireDTO> mettreAJourStatut(@PathVariable Long idTache, @RequestBody TachePrestataireDTO dto) {
        return ResponseEntity.ok(tacheService.mettreAJourStatut(idTache, dto));
    }

    @GetMapping("/prestataire/{idPrestataire}")
    public ResponseEntity<List<TachePrestataireDTO>> obtenirTaches(@PathVariable Long idPrestataire) {
        return ResponseEntity.ok(tacheService.obtenirTachesParPrestataire(idPrestataire));
    }
}