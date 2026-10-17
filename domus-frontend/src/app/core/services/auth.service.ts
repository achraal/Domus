import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { AuthRequestDTO, AuthResponseDTO, UtilisateurResponseDTO } from '../models/auth.model'; // Import de UtilisateurResponseDTO ajouté
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
  } // <-- La méthode login se ferme ici

  inscription(utilisateur: any): Observable<UtilisateurResponseDTO> {
    return this.http.post<UtilisateurResponseDTO>(`${this.apiUrl}/inscription`, utilisateur);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('utilisateur');
  }

  estConnecte(): boolean {
    return !!localStorage.getItem('token');
  }
  getUtilisateurConnecte(): any {
    const userStr = localStorage.getItem('utilisateur');
    return userStr ? JSON.parse(userStr) : null;
  }

  aLeRole(roleAttendu: string): boolean {
    const user = this.getUtilisateurConnecte();
    if (!user) return false;

    // Adaptez selon la structure de votre UtilisateurDTO backend (ex: user.role ou user.roles)
    if (Array.isArray(user.roles)) {
      return user.roles.includes(roleAttendu) || user.roles.includes(`ROLE_${roleAttendu}`);
    }

    return user.role === roleAttendu || user.role === `ROLE_${roleAttendu}`;
  }
}
