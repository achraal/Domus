package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.*;
import com.achraf.domusproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportController {
    private final RapportService rapportService;

    @PostMapping("/generer")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<RapportDTO> genererRapport(@RequestBody RapportDTO dto) {
        return ResponseEntity.ok(rapportService.genererRapport(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<List<RapportDTO>> obtenirRapports() {
        return ResponseEntity.ok(rapportService.obtenirRapports());
    }
}