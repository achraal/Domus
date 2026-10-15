package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.EtatAppartementDTO;
import java.util.List;

public interface EtatAppartementService {
    EtatAppartementDTO mettreAJourEtat(EtatAppartementDTO dto);
    List<EtatAppartementDTO> obtenirHistorique(Long idAnnonce);
    EtatAppartementDTO obtenirEtatActuel(Long idAnnonce);
}