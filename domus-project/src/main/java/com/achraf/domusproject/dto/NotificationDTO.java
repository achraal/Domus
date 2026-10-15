package com.achraf.domusproject.dto;

import com.achraf.domusproject.enums.NotificationType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long idNotification;
    private Long idUtilisateur;
    private NotificationType type;
    private String contenu;
    private LocalDateTime dateCreation;
    private boolean lue;
}