package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.*;
import java.util.List;

public interface RapportService {
    RapportDTO genererRapport(RapportDTO dto); // Génération automatique
    List<RapportDTO> obtenirRapports();
}