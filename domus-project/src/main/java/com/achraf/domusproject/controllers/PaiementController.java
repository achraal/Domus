package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.PaiementDTO;
import com.achraf.domusproject.dto.QuittanceDTO;
import com.achraf.domusproject.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('LOCATAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<PaiementDTO> effectuerPaiement(@RequestBody PaiementDTO dto) {
        return ResponseEntity.ok(paiementService.traiterPaiement(dto));
    }

    @GetMapping("/reservation/{idReservation}")
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'LOCATAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<List<PaiementDTO>> obtenirPaiements(@PathVariable Long idReservation) {
        return ResponseEntity.ok(paiementService.obtenirPaiementsParReservation(idReservation));
    }

    @GetMapping("/{idPaiement}/quittance")
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'LOCATAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<QuittanceDTO> telechargerQuittance(@PathVariable Long idPaiement) {
        return ResponseEntity.ok(paiementService.obtenirQuittanceParPaiement(idPaiement));
    }

    @PostMapping("/reservation/{idReservation}/rembourser-depot")
    @PreAuthorize("hasAnyRole('PROPRIETAIRE', 'ADMINISTRATEUR')")
    public ResponseEntity<PaiementDTO> rembourserDepot(@PathVariable Long idReservation) {
        return ResponseEntity.ok(paiementService.rembourserDepotGarantie(idReservation));
    }
}