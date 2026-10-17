import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TarificationDTO } from '../models/tarification.model';

@Injectable({
  providedIn: 'root'
})
export class TarificationService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/tarifications`;

  /**
   * POST /api/tarifications
   * Rôles requis : PROPRIETAIRE, ADMINISTRATEUR
   */
  ajouterTarification(request: TarificationDTO): Observable<TarificationDTO> {
    return this.http.post<TarificationDTO>(this.apiUrl, request);
  }

  /**
   * GET /api/tarifications/annonce/{idAnnonce}
   * Accessible à tous
   */
  obtenirTarifications(idAnnonce: number): Observable<TarificationDTO[]> {
    return this.http.get<TarificationDTO[]>(`${this.apiUrl}/annonce/${idAnnonce}`);
  }

  /**
   * DELETE /api/tarifications/{id}
   * Rôles requis : PROPRIETAIRE, ADMINISTRATEUR
   */
  supprimerTarification(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /**
   * GET /api/tarifications/annonce/{idAnnonce}/prix?date=YYYY-MM-DD
   * Accessible à tous
   */
  calculerPrixPourDate(idAnnonce: number, date: string): Observable<number> {
    const params = new HttpParams().set('date', date);
    return this.http.get<number>(`${this.apiUrl}/annonce/${idAnnonce}/prix`, { params });
  }
}
