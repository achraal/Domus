package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.TarificationDTO;
import com.achraf.domusproject.service.TarificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarifications")
@RequiredArgsConstructor
public class TarificationController {

    private final TarificationService tarificationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<TarificationDTO> ajouterTarification(@RequestBody TarificationDTO request) {
        return ResponseEntity.ok(tarificationService.ajouterTarification(request));
    }

    @GetMapping("/annonce/{idAnnonce}")
    public ResponseEntity<List<TarificationDTO>> obtenirTarifications(@PathVariable Long idAnnonce) {
        return ResponseEntity.ok(tarificationService.obtenirTarifications(idAnnonce));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<Void> supprimerTarification(@PathVariable Long id) {
        tarificationService.supprimerTarification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/annonce/{idAnnonce}/prix")
    public ResponseEntity<BigDecimal> calculerPrix(
            @PathVariable Long idAnnonce,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(tarificationService.calculerPrixPourDate(idAnnonce, date));
    }
}