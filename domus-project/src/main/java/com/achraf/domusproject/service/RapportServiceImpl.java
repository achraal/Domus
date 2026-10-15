package com.achraf.domusproject.service.impl;

import com.achraf.domusproject.dto.RapportDTO;
import com.achraf.domusproject.entities.Rapport;
import com.achraf.domusproject.repository.RapportRepository;
import com.achraf.domusproject.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RapportServiceImpl implements RapportService {

    private final RapportRepository rapportRepo;

    @Override
    public RapportDTO genererRapport(RapportDTO dto) {
        // La logique d'agrégation complexe (calcul des revenus/taux d'occupation) irait ici
        // Pour cet exemple, on sauvegarde le payload généré
        Rapport rapport = Rapport.builder()
                .typeRapport(dto.getTypeRapport())
                .periode(dto.getPeriode())
                .dateGeneration(LocalDateTime.now())
                .contenu(dto.getContenu()).build();

        return mapDTO(rapportRepo.save(rapport));
    }

    @Override
    public List<RapportDTO> obtenirRapports() {
        return rapportRepo.findAll().stream().map(this::mapDTO).collect(Collectors.toList());
    }

    private RapportDTO mapDTO(Rapport r) {
        return RapportDTO.builder().idRapport(r.getIdRapport())
                .typeRapport(r.getTypeRapport()).periode(r.getPeriode())
                .dateGeneration(r.getDateGeneration()).contenu(r.getContenu()).build();
    }
}