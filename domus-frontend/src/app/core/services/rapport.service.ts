import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { RapportDTO } from '../models/rapport.model';

@Injectable({
  providedIn: 'root'
})
export class RapportService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/rapports`;

  /**
   * POST /api/rapports/generer
   * Rôle : ADMINISTRATEUR
   */
  genererRapport(dto: RapportDTO): Observable<RapportDTO> {
    return this.http.post<RapportDTO>(`${this.apiUrl}/generer`, dto);
  }

  /**
   * GET /api/rapports
   * Rôle : ADMINISTRATEUR
   */
  obtenirRapports(): Observable<RapportDTO[]> {
    return this.http.get<RapportDTO[]>(this.apiUrl);
  }
}
