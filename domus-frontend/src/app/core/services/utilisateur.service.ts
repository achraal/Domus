import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { UtilisateurResponseDTO } from '../models/auth.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/utilisateurs`;

  obtenirTous(): Observable<UtilisateurResponseDTO[]> {
    return this.http.get<UtilisateurResponseDTO[]>(this.apiUrl);
  }

  supprimerCompte(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
