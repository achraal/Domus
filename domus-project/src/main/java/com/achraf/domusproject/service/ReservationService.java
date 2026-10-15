package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.ReservationRequestDTO;
import com.achraf.domusproject.dto.ReservationResponseDTO;
import com.achraf.domusproject.enums.StatutReservation;
import java.util.List;

public interface ReservationService {
    ReservationResponseDTO creerReservation(ReservationRequestDTO request, String emailLocataire);
    ReservationResponseDTO changerStatutReservation(Long idReservation, StatutReservation nouveauStatut, String emailUtilisateur);
    void annulerReservation(Long idReservation, String emailUtilisateur);
    ReservationResponseDTO obtenirParId(Long id);
    List<ReservationResponseDTO> obtenirReservationsParLocataire(String emailLocataire);
    List<ReservationResponseDTO> obtenirReservationsParAnnonce(Long idAnnonce);
}