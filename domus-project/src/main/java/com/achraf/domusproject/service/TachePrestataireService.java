package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.*;
import java.util.List;

public interface TachePrestataireService {
    TachePrestataireDTO assignerTache(TachePrestataireDTO dto);
    TachePrestataireDTO mettreAJourStatut(Long idTache, TachePrestataireDTO dto);
    List<TachePrestataireDTO> obtenirTachesParPrestataire(Long idPrestataire);
}