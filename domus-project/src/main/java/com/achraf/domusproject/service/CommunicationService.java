package com.achraf.domusproject.service;

import com.achraf.domusproject.dto.*;
import java.util.List;

public interface CommunicationService {
    MessageDTO envoyerMessage(MessageDTO dto);
    List<MessageDTO> obtenirMessagesUtilisateur(Long idUtilisateur);
    NotificationDTO creerNotification(NotificationDTO dto);
    List<NotificationDTO> obtenirNotifications(Long idUtilisateur);
    void marquerNotificationLue(Long idNotification);
}