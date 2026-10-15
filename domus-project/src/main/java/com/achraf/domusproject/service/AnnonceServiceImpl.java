package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.AnnonceRequestDTO;
import com.achraf.domusproject.dto.AnnonceResponseDTO;
import com.achraf.domusproject.entities.Annonce;
import com.achraf.domusproject.entities.Utilisateur;
import com.achraf.domusproject.enums.EtatType;
import com.achraf.domusproject.repository.AnnonceRepository;
import com.achraf.domusproject.repository.UtilisateurRepository;
import com.achraf.domusproject.service.AnnonceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public AnnonceResponseDTO creerAnnonce(AnnonceRequestDTO request, String emailProprietaire) {
        Utilisateur proprietaire = utilisateurRepository.findByEmail(emailProprietaire)
                .orElseThrow(() -> new RuntimeException("Propriétaire introuvable"));

        Annonce annonce = Annonce.builder()
                .proprietaire(proprietaire)
                .titre(request.getTitre())
                .description(request.getDescription())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .codePostal(request.getCodePostal())
                .nombreDeChambres(request.getNombreDeChambres())
                .superficie(request.getSuperficie())
                .prixParNuit(request.getPrixParNuit())
                .photos(request.getPhotos())
                .caracteristiques(request.getCaracteristiques())
                .etat(request.getEtat() != null ? request.getEtat() : EtatType.LIBRE)
                .build();

        return mapToDTO(annonceRepository.save(annonce));
    }

    @Override
    @Transactional
    public AnnonceResponseDTO modifierAnnonce(Long id, AnnonceRequestDTO request, String emailProprietaire) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        if (!annonce.getProprietaire().getEmail().equals(emailProprietaire)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette annonce");
        }

        annonce.setTitre(request.getTitre());
        annonce.setDescription(request.getDescription());
        annonce.setAdresse(request.getAdresse());
        annonce.setVille(request.getVille());
        annonce.setCodePostal(request.getCodePostal());
        annonce.setPrixParNuit(request.getPrixParNuit());
        annonce.setPhotos(request.getPhotos());
        annonce.setCaracteristiques(request.getCaracteristiques());
        if(request.getEtat() != null) annonce.setEtat(request.getEtat());

        return mapToDTO(annonceRepository.save(annonce));
    }

    @Override
    public void supprimerAnnonce(Long id, String emailProprietaire) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        if (!annonce.getProprietaire().getEmail().equals(emailProprietaire)) {
            throw new RuntimeException("Action non autorisée");
        }
        annonceRepository.delete(annonce);
    }

    @Override
    public AnnonceResponseDTO obtenirParId(Long id) {
        return mapToDTO(annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable")));
    }

    @Override
    public List<AnnonceResponseDTO> obtenirToutesAnnoncesDisponibles() {
        return annonceRepository.findByEtat(EtatType.LIBRE).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnonceResponseDTO> obtenirAnnoncesParProprietaire(String emailProprietaire) {
        Utilisateur proprietaire = utilisateurRepository.findByEmail(emailProprietaire)
                .orElseThrow(() -> new RuntimeException("Propriétaire introuvable"));

        return annonceRepository.findByProprietaireIdUtilisateur(proprietaire.getIdUtilisateur()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AnnonceResponseDTO mapToDTO(Annonce annonce) {
        return AnnonceResponseDTO.builder()
                .idAnnonce(annonce.getIdAnnonce())
                .idProprietaire(annonce.getProprietaire().getIdUtilisateur())
                .nomProprietaire(annonce.getProprietaire().getNom() + " " + annonce.getProprietaire().getPrenom())
                .titre(annonce.getTitre())
                .description(annonce.getDescription())
                .adresse(annonce.getAdresse())
                .ville(annonce.getVille())
                .codePostal(annonce.getCodePostal())
                .nombreDeChambres(annonce.getNombreDeChambres())
                .superficie(annonce.getSuperficie())
                .prixParNuit(annonce.getPrixParNuit())
                .photos(annonce.getPhotos())
                .caracteristiques(annonce.getCaracteristiques())
                .etat(annonce.getEtat())
                .build();
    }
}