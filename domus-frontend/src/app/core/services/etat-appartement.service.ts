import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EtatAppartementDTO } from '../models/etat-appartement.model';

@Injectable({
  providedIn: 'root'
})
export class EtatAppartementService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/etats`;

  /**
   * POST /api/etats
   * Rôles requis : PROPRIETAIRE, ADMINISTRATEUR
   */
  mettreAJourEtat(request: EtatAppartementDTO): Observable<EtatAppartementDTO> {
    return this.http.post<EtatAppartementDTO>(this.apiUrl, request);
  }

  /**
   * GET /api/etats/annonce/{idAnnonce}/historique
   * Accessible à tous
   */
  obtenirHistorique(idAnnonce: number): Observable<EtatAppartementDTO[]> {
    return this.http.get<EtatAppartementDTO[]>(`${this.apiUrl}/annonce/${idAnnonce}/historique`);
  }

  /**
   * GET /api/etats/annonce/{idAnnonce}/actuel
   * Accessible à tous
   */
  obtenirEtatActuel(idAnnonce: number): Observable<EtatAppartementDTO> {
    return this.http.get<EtatAppartementDTO>(`${this.apiUrl}/annonce/${idAnnonce}/actuel`);
  }
}
