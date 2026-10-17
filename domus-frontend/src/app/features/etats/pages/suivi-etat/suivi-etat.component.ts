import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EtatAppartementService } from '../../../../core/services/etat-appartement.service';
import { EtatAppartementDTO, StatutAppartement } from '../../../../core/models/etat-appartement.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-suivi-etat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './suivi-etat.component.html'
})
export class SuiviEtatComponent implements OnInit {
  private fb = inject(FormBuilder);
  private etatService = inject(EtatAppartementService);
  private route = inject(ActivatedRoute);
  public authService = inject(AuthService);

  idAnnonce!: number;
  etatActuel?: EtatAppartementDTO;
  historique: EtatAppartementDTO[] = [];
  statutForm!: FormGroup;

  statutsDisponibles = Object.values(StatutAppartement);
  chargement = true;
  soumissionEnCours = false;
  erreur = '';
  succes = '';

  ngOnInit(): void {
    this.idAnnonce = Number(this.route.snapshot.paramMap.get('idAnnonce'));

    this.statutForm = this.fb.group({
      statut: [StatutAppartement.LIBRE, [Validators.required]],
      commentaire: ['']
    });

    this.chargerDonnees();
  }

  chargerDonnees(): void {
    this.chargement = true;

    // Récupération état actuel
    this.etatService.obtenirEtatActuel(this.idAnnonce).subscribe({
      next: (data) => {
        this.etatActuel = data;
        if (data) {
          this.statutForm.patchValue({ statut: data.statut });
        }
      },
      error: () => console.log('Aucun état actuel défini')
    });

    // Récupération historique
    this.etatService.obtenirHistorique(this.idAnnonce).subscribe({
      next: (data) => {
        this.historique = data;
        this.chargement = false;
      },
      error: () => {
        this.chargement = false;
        this.erreur = 'Erreur lors du chargement de l\'historique';
      }
    });
  }

  soumettreChangement(): void {
    if (this.statutForm.invalid) return;

    this.soumissionEnCours = true;
    this.erreur = '';
    this.succes = '';

    const payload: EtatAppartementDTO = {
      annonceId: this.idAnnonce,
      statut: this.statutForm.value.statut,
      commentaire: this.statutForm.value.commentaire
    };

    this.etatService.mettreAJourEtat(payload).subscribe({
      next: () => {
        this.soumissionEnCours = false;
        this.succes = 'Statut mis à jour avec succès !';
        this.statutForm.get('commentaire')?.reset();
        this.chargerDonnees();
      },
      error: (err) => {
        this.soumissionEnCours = false;
        this.erreur = err.error?.message || 'Erreur lors de la mise à jour du statut.';
      }
    });
  }

  getBadgeClass(statut: StatutAppartement): string {
    switch (statut) {
      case StatutAppartement.LIBRE: return 'bg-success';
      case StatutAppartement.OCCUPE: return 'bg-danger';
      case StatutAppartement.MAINTENANCE: return 'bg-warning text-dark';
      default: return 'bg-secondary';
    }
  }
}
