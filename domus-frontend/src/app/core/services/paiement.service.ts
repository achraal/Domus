import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PaiementDTO, QuittanceDTO } from '../models/paiement.model';

@Injectable({
  providedIn: 'root'
})
export class PaiementService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/paiements`;

  /**
   * POST /api/paiements
   * Rôles : LOCATAIRE, ADMINISTRATEUR
   */
  effectuerPaiement(dto: PaiementDTO): Observable<PaiementDTO> {
    return this.http.post<PaiementDTO>(this.apiUrl, dto);
  }

  /**
   * GET /api/paiements/reservation/{idReservation}
   * Rôles : PROPRIETAIRE, LOCATAIRE, ADMINISTRATEUR
   */
  obtenirPaiementsParReservation(idReservation: number): Observable<PaiementDTO[]> {
    return this.http.get<PaiementDTO[]>(`${this.apiUrl}/reservation/${idReservation}`);
  }

  /**
   * GET /api/paiements/{idPaiement}/quittance
   * Rôles : PROPRIETAIRE, LOCATAIRE, ADMINISTRATEUR
   */
  obtenirQuittanceParPaiement(idPaiement: number): Observable<QuittanceDTO> {
    return this.http.get<QuittanceDTO>(`${this.apiUrl}/${idPaiement}/quittance`);
  }

  /**
   * POST /api/paiements/reservation/{idReservation}/rembourser-depot
   * Rôles : PROPRIETAIRE, ADMINISTRATEUR
   */
  rembourserDepot(idReservation: number): Observable<PaiementDTO> {
    return this.http.post<PaiementDTO>(`${this.apiUrl}/reservation/${idReservation}/rembourser-depot`, {});
  }
}
