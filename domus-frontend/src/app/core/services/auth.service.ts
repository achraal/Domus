import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { AuthRequestDTO, AuthResponseDTO } from '../models/auth.model';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/auth`;

  login(credentials: AuthRequestDTO): Observable<AuthResponseDTO> {
    return this.http.post<AuthResponseDTO>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        // On stocke le token pour l'intercepteur et les infos de l'utilisateur
        localStorage.setItem('token', response.token);
        localStorage.setItem('utilisateur', JSON.stringify(response.utilisateur));
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('utilisateur');
  }

  estConnecte(): boolean {
    return !!localStorage.getItem('token');
  }
}
