package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.TarificationDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TarificationService {
    TarificationDTO ajouterTarification(TarificationDTO dto);
    List<TarificationDTO> obtenirTarifications(Long idAnnonce);
    void supprimerTarification(Long idTarification);
    BigDecimal calculerPrixPourDate(Long idAnnonce, LocalDate date);
}