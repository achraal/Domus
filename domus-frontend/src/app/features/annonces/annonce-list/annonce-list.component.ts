import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AnnonceService } from '../../../core/services/annonce.service';
import { AnnonceResponseDTO } from '../../../core/models/annonce.model';

@Component({
  selector: 'app-annonce-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './annonce-list.component.html'
})
export class AnnonceListComponent implements OnInit {
  private annonceService = inject(AnnonceService);

  annonces: AnnonceResponseDTO[] = [];
  mesAnnonces: AnnonceResponseDTO[] = [];
  affichageMesAnnonces = false;
  chargement = true;
  erreur = '';

  ngOnInit(): void {
    this.chargerToutesLesAnnonces();
  }

  // GET /api/annonces
  chargerToutesLesAnnonces(): void {
    this.chargement = true;
    this.affichageMesAnnonces = false;
    this.annonceService.obtenirToutesAnnonces().subscribe({
      next: (data) => {
        this.annonces = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Erreur lors du chargement des annonces';
        this.chargement = false;
      }
    });
  }

  // GET /api/annonces/mes-annonces
  chargerMesAnnonces(): void {
    this.chargement = true;
    this.affichageMesAnnonces = true;
    this.annonceService.obtenirMesAnnonces().subscribe({
      next: (data) => {
        this.mesAnnonces = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Erreur lors de la récupération de vos annonces';
        this.chargement = false;
      }
    });
  }

  // DELETE /api/annonces/{id}
  supprimer(id: number): void {
    if (confirm('Voulez-vous vraiment supprimer cette annonce ?')) {
      this.annonceService.supprimerAnnonce(id).subscribe({
        next: () => {
          this.annonces = this.annonces.filter(a => a.id !== id);
          this.mesAnnonces = this.mesAnnonces.filter(a => a.id !== id);
        },
        error: (err) => alert('Erreur lors de la suppression')
      });
    }
  }
}
