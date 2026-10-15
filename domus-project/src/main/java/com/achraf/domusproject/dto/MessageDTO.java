package com.achraf.domusproject.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MessageDTO {
    private Long idMessage;
    private Long idExpediteur;
    private Long idDestinataire;
    private LocalDateTime dateMessage;
    private String contenu;
}