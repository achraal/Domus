package com.achraf.domusproject.service.impl;

import com.achraf.domusproject.dto.*;
import com.achraf.domusproject.entities.*;
import com.achraf.domusproject.repository.*;
import com.achraf.domusproject.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

    private final MessageRepository messageRepo;
    private final NotificationRepository notifRepo;
    private final UtilisateurRepository userRepo;

    @Override
    public MessageDTO envoyerMessage(MessageDTO dto) {
        Utilisateur exp = userRepo.findById(dto.getIdExpediteur()).orElseThrow();
        Utilisateur dest = userRepo.findById(dto.getIdDestinataire()).orElseThrow();

        Message msg = Message.builder()
                .expediteur(exp).destinataire(dest)
                .dateMessage(LocalDateTime.now())
                .contenu(dto.getContenu()).build();

        msg = messageRepo.save(msg);

        // Création automatique d'une notification pour le destinataire
        creerNotification(NotificationDTO.builder()
                .idUtilisateur(dest.getIdUtilisateur())
                .type(com.achraf.domusproject.enums.NotificationType.MESSAGE)
                .contenu("Nouveau message de " + exp.getNom())
                .build());

        return mapMessageDTO(msg);
    }

    @Override
    public List<MessageDTO> obtenirMessagesUtilisateur(Long idUtilisateur) {
        return messageRepo.findByExpediteurIdUtilisateurOrDestinataireIdUtilisateur(idUtilisateur, idUtilisateur)
                .stream().map(this::mapMessageDTO).collect(Collectors.toList());
    }

    @Override
    public NotificationDTO creerNotification(NotificationDTO dto) {
        Utilisateur user = userRepo.findById(dto.getIdUtilisateur()).orElseThrow();
        Notification notif = Notification.builder()
                .utilisateur(user)
                .type(dto.getType())
                .contenu(dto.getContenu())
                .dateCreation(LocalDateTime.now())
                .lue(false).build();
        return mapNotifDTO(notifRepo.save(notif));
    }

    @Override
    public List<NotificationDTO> obtenirNotifications(Long idUtilisateur) {
        return notifRepo.findByUtilisateurIdUtilisateurOrderByDateCreationDesc(idUtilisateur)
                .stream().map(this::mapNotifDTO).collect(Collectors.toList());
    }

    @Override
    public void marquerNotificationLue(Long idNotification) {
        Notification notif = notifRepo.findById(idNotification).orElseThrow();
        notif.setLue(true);
        notifRepo.save(notif);
    }

    private MessageDTO mapMessageDTO(Message m) {
        return MessageDTO.builder().idMessage(m.getIdMessage())
                .idExpediteur(m.getExpediteur().getIdUtilisateur())
                .idDestinataire(m.getDestinataire().getIdUtilisateur())
                .dateMessage(m.getDateMessage()).contenu(m.getContenu()).build();
    }

    private NotificationDTO mapNotifDTO(Notification n) {
        return NotificationDTO.builder().idNotification(n.getIdNotification())
                .idUtilisateur(n.getUtilisateur().getIdUtilisateur())
                .type(n.getType()).contenu(n.getContenu())
                .dateCreation(n.getDateCreation()).lue(n.isLue()).build();
    }
}