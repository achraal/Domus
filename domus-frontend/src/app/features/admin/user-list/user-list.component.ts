import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtilisateurService } from '../../../core/services/utilisateur.service';
import { UtilisateurResponseDTO } from '../../../core/models/auth.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  private userService = inject(UtilisateurService);

  utilisateurs: UtilisateurResponseDTO[] = [];
  isLoading = true;

  ngOnInit() {
    this.chargerUtilisateurs();
  }

  chargerUtilisateurs() {
    this.userService.obtenirTous().subscribe({
      next: (data) => {
        this.utilisateurs = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des utilisateurs', err);
        this.isLoading = false;
      }
    });
  }

  supprimerUtilisateur(id: number, nom: string) {
    if (confirm(`Êtes-vous sûr de vouloir supprimer l'utilisateur ${nom} ?`)) {
      this.userService.supprimerCompte(id).subscribe({
        next: () => {
          // Mettre à jour la liste localement après suppression
          this.utilisateurs = this.utilisateurs.filter(u => u.idUtilisateur !== id);
        },
        error: (err) => console.error('Erreur lors de la suppression', err)
      });
    }
  }
}
