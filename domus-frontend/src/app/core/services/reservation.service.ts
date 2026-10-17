import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  ReservationRequestDTO,
  ReservationResponseDTO,
  StatutReservation
} from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/reservations`;

  /**
   * POST /api/reservations
   * Rôle requis backend : LOCATAIRE
   */
  creerReservation(request: ReservationRequestDTO): Observable<ReservationResponseDTO> {
    return this.http.post<ReservationResponseDTO>(this.apiUrl, request);
  }

  /**
   * PATCH /api/reservations/{id}/statut?statut=...
   * Rôles requis backend : PROPRIETAIRE ou ADMINISTRATEUR
   */
  changerStatut(id: number, statut: StatutReservation): Observable<ReservationResponseDTO> {
    const params = new HttpParams().set('statut', statut);
    return this.http.patch<ReservationResponseDTO>(`${this.apiUrl}/${id}/statut`, null, { params });
  }

  /**
   * POST /api/reservations/{id}/annuler
   * Rôles requis backend : LOCATAIRE ou PROPRIETAIRE
   */
  annulerReservation(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/annuler`, {});
  }

  /**
   * GET /api/reservations/mes-reservations
   * Rôle requis backend : LOCATAIRE
   */
  obtenirMesReservations(): Observable<ReservationResponseDTO[]> {
    return this.http.get<ReservationResponseDTO[]>(`${this.apiUrl}/mes-reservations`);
  }

  /**
   * GET /api/reservations/annonce/{idAnnonce}
   * Rôles requis backend : PROPRIETAIRE ou ADMINISTRATEUR
   */
  obtenirReservationsParAnnonce(idAnnonce: number): Observable<ReservationResponseDTO[]> {
    return this.http.get<ReservationResponseDTO[]>(`${this.apiUrl}/annonce/${idAnnonce}`);
  }
}
