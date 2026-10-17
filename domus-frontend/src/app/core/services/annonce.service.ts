import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AnnonceRequestDTO, AnnonceResponseDTO } from '../models/annonce.model';

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/annonces`;

  /**
   * POST /api/annonces
   * Rôle requis backend : PROPRIETAIRE
   */
  creerAnnonce(request: AnnonceRequestDTO): Observable<AnnonceResponseDTO> {
    return this.http.post<AnnonceResponseDTO>(this.apiUrl, request);
  }

  /**
   * PUT /api/annonces/{id}
   * Rôle requis backend : PROPRIETAIRE
   */
  modifierAnnonce(id: number, request: AnnonceRequestDTO): Observable<AnnonceResponseDTO> {
    return this.http.put<AnnonceResponseDTO>(`${this.apiUrl}/${id}`, request);
  }

  /**
   * DELETE /api/annonces/{id}
   * Rôles requis backend : PROPRIETAIRE ou ADMINISTRATEUR
   */
  supprimerAnnonce(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /**
   * GET /api/annonces
   * Accessible à tous
   */
  obtenirToutesAnnonces(): Observable<AnnonceResponseDTO[]> {
    return this.http.get<AnnonceResponseDTO[]>(this.apiUrl);
  }

  /**
   * GET /api/annonces/{id}
   * Accessible à tous
   */
  obtenirAnnonceParId(id: number): Observable<AnnonceResponseDTO> {
    return this.http.get<AnnonceResponseDTO>(`${this.apiUrl}/${id}`);
  }

  /**
   * GET /api/annonces/mes-annonces
   * Rôle requis backend : PROPRIETAIRE
   */
  obtenirMesAnnonces(): Observable<AnnonceResponseDTO[]> {
    return this.http.get<AnnonceResponseDTO[]>(`${this.apiUrl}/mes-annonces`);
  }
}
