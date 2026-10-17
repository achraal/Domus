import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommunicationService } from '../../../../core/services/communication.service';
import { MessageDTO, NotificationDTO } from '../../../../core/models/communication.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-messagerie',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './messagerie.component.html'
})
export class MessagerieComponent implements OnInit {
  private fb = inject(FormBuilder);
  private commService = inject(CommunicationService);
  public authService = inject(AuthService);

  messages: MessageDTO[] = [];
  notifications: NotificationDTO[] = [];
  messageForm!: FormGroup;

  currentUserId!: number;
  chargementMessages = true;
  chargementNotifs = true;
  envoiEnCours = false;

  ngOnInit(): void {
    const user = this.authService.getUtilisateurConnecte();
    if (user) {
      this.currentUserId = user.id;
    }

    this.messageForm = this.fb.group({
      destinataireId: ['', [Validators.required]],
      contenu: ['', [Validators.required]]
    });

    this.chargerMessages();
    this.chargerNotifications();
  }

  chargerMessages(): void {
    if (!this.currentUserId) return;
    this.chargementMessages = true;
    this.commService.obtenirMessages(this.currentUserId).subscribe({
      next: (data) => {
        this.messages = data;
        this.chargementMessages = false;
      },
      error: () => this.chargementMessages = false
    });
  }

  chargerNotifications(): void {
    if (!this.currentUserId) return;
    this.chargementNotifs = true;
    this.commService.obtenirNotifications(this.currentUserId).subscribe({
      next: (data) => {
        this.notifications = data;
        this.chargementNotifs = false;
      },
      error: () => this.chargementNotifs = false
    });
  }

  envoyer(): void {
    if (this.messageForm.invalid) return;

    this.envoiEnCours = true;
    const dto: MessageDTO = {
      expediteurId: this.currentUserId,
      ...this.messageForm.value
    };

    this.commService.envoyerMessage(dto).subscribe({
      next: () => {
        this.envoiEnCours = false;
        this.messageForm.get('contenu')?.reset();
        this.chargerMessages();
      },
      error: () => {
        this.envoiEnCours = false;
        alert('Erreur lors de l\'envoi du message');
      }
    });
  }

  marquerCommeLue(notifId: number): void {
    this.commService.marquerLue(notifId).subscribe({
      next: () => {
        const notif = this.notifications.find(n => n.id === notifId);
        if (notif) notif.lue = true;
      }
    });
  }
}
