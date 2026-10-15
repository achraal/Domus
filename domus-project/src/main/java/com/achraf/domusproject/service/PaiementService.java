package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.PaiementDTO;
import com.achraf.domusproject.dto.QuittanceDTO;
import java.util.List;

public interface PaiementService {
    PaiementDTO traiterPaiement(PaiementDTO dto);
    List<PaiementDTO> obtenirPaiementsParReservation(Long idReservation);
    QuittanceDTO genererQuittance(Long idPaiement);
    QuittanceDTO obtenirQuittanceParPaiement(Long idPaiement);
    PaiementDTO rembourserDepotGarantie(Long idReservation);
}