package com.achraf.domusproject.controllers;

import com.achraf.domusproject.dto.*;
import com.achraf.domusproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communication")
@RequiredArgsConstructor
public class CommunicationController {
    private final CommunicationService commService;

    @PostMapping("/message")
    public ResponseEntity<MessageDTO> envoyerMessage(@RequestBody MessageDTO dto) {
        return ResponseEntity.ok(commService.envoyerMessage(dto));
    }

    @GetMapping("/messages/{idUtilisateur}")
    public ResponseEntity<List<MessageDTO>> obtenirMessages(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(commService.obtenirMessagesUtilisateur(idUtilisateur));
    }

    @GetMapping("/notifications/{idUtilisateur}")
    public ResponseEntity<List<NotificationDTO>> obtenirNotifications(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(commService.obtenirNotifications(idUtilisateur));
    }

    @PutMapping("/notifications/{idNotif}/lire")
    public ResponseEntity<Void> marquerLue(@PathVariable Long idNotif) {
        commService.marquerNotificationLue(idNotif);
        return ResponseEntity.ok().build();
    }
}