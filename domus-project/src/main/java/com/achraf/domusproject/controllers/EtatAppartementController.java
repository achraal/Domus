package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.EtatAppartementDTO;
import com.achraf.domusproject.service.EtatAppartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etats")
@RequiredArgsConstructor
public class EtatAppartementController {

    private final EtatAppartementService etatService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<EtatAppartementDTO> mettreAJourEtat(@RequestBody EtatAppartementDTO request) {
        return ResponseEntity.ok(etatService.mettreAJourEtat(request));
    }

    @GetMapping("/annonce/{idAnnonce}/historique")
    public ResponseEntity<List<EtatAppartementDTO>> obtenirHistorique(@PathVariable Long idAnnonce) {
        return ResponseEntity.ok(etatService.obtenirHistorique(idAnnonce));
    }

    @GetMapping("/annonce/{idAnnonce}/actuel")
    public ResponseEntity<EtatAppartementDTO> obtenirEtatActuel(@PathVariable Long idAnnonce) {
        return ResponseEntity.ok(etatService.obtenirEtatActuel(idAnnonce));
    }
}