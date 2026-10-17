import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TachePrestataireDTO } from '../models/tache.model';

@Injectable({
  providedIn: 'root'
})
export class TachePrestataireService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/taches`;

  /**
   * POST /api/taches
   * Rôles : PROPRIETAIRE, ADMINISTRATEUR
   */
  assignerTache(dto: TachePrestataireDTO): Observable<TachePrestataireDTO> {
    return this.http.post<TachePrestataireDTO>(this.apiUrl, dto);
  }

  /**
   * PUT /api/taches/{idTache}/statut
   * Rôles : PRESTATAIRE, ADMINISTRATEUR
   */
  mettreAJourStatut(idTache: number, dto: TachePrestataireDTO): Observable<TachePrestataireDTO> {
    return this.http.put<TachePrestataireDTO>(`${this.apiUrl}/${idTache}/statut`, dto);
  }

  /**
   * GET /api/taches/prestataire/{idPrestataire}
   */
  obtenirTaches(idPrestataire: number): Observable<TachePrestataireDTO[]> {
    return this.http.get<TachePrestataireDTO[]>(`${this.apiUrl}/prestataire/${idPrestataire}`);
  }
}
