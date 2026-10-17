import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MessageDTO, NotificationDTO } from '../models/communication.model';

@Injectable({
  providedIn: 'root'
})
export class CommunicationService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/communication`;

  /**
   * POST /api/communication/message
   */
  envoyerMessage(dto: MessageDTO): Observable<MessageDTO> {
    return this.http.post<MessageDTO>(`${this.apiUrl}/message`, dto);
  }

  /**
   * GET /api/communication/messages/{idUtilisateur}
   */
  obtenirMessages(idUtilisateur: number): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/messages/${idUtilisateur}`);
  }

  /**
   * GET /api/communication/notifications/{idUtilisateur}
   */
  obtenirNotifications(idUtilisateur: number): Observable<NotificationDTO[]> {
    return this.http.get<NotificationDTO[]>(`${this.apiUrl}/notifications/${idUtilisateur}`);
  }

  /**
   * PUT /api/communication/notifications/{idNotif}/lire
   */
  marquerLue(idNotif: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/notifications/${idNotif}/lire`, {});
  }
}
