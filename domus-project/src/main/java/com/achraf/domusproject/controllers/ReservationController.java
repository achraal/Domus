package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.ReservationRequestDTO;
import com.achraf.domusproject.dto.ReservationResponseDTO;
import com.achraf.domusproject.enums.StatutReservation;
import com.achraf.domusproject.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('LOCATAIRE')")
    public ResponseEntity<ReservationResponseDTO> creerReservation(@RequestBody ReservationRequestDTO request, Authentication authentication) {
        return ResponseEntity.ok(reservationService.creerReservation(request, authentication.getName()));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasRole('PROPRIETAIRE') or hasRole('ADMINISTRATEUR')")
    public ResponseEntity<ReservationResponseDTO> changerStatut(
            @PathVariable Long id,
            @RequestParam StatutReservation statut,
            Authentication authentication) {
        return ResponseEntity.ok(reservationService.changerStatutReservation(id, statut, authentication.getName()));
    }

    @PostMapping("/{id}/annuler")
    @PreAuthorize("hasRole('LOCATAIRE') or hasRole('PROPRIETAIRE')")
    public ResponseEntity<Void> annulerReservation(@PathVariable Long id, Authentication authentication) {
        reservationService.annulerReservation(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mes-reservations")
    @PreAuthorize("hasRole('LOCATAIRE')")
    public ResponseEntity<List<ReservationResponseDTO>> obtenirMesReservations(Authentication authentication) {
        return ResponseEntity.ok(reservationService.obtenirReservationsParLocataire(authentication.getName()));
    }

    @GetMapping("/annonce/{idAnnonce}")
    @PreAuthorize("hasRole('PROPRIETAIRE') or hasRole('ADMINISTRATEUR')")
    public ResponseEntity<List<ReservationResponseDTO>> obtenirReservationsParAnnonce(@PathVariable Long idAnnonce) {
        return ResponseEntity.ok(reservationService.obtenirReservationsParAnnonce(idAnnonce));
    }
}