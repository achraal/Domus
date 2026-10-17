import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TachePrestataireService } from '../../../../core/services/tache-prestataire.service';
import { StatutTache, TachePrestataireDTO } from '../../../../core/models/tache.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-tache-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './tache-list.component.html'
})
export class TacheListComponent implements OnInit {
  private fb = inject(FormBuilder);
  private tacheService = inject(TachePrestataireService);
  public authService = inject(AuthService);

  taches: TachePrestataireDTO[] = [];
  tacheForm!: FormGroup;
  statuts = Object.values(StatutTache);
  chargement = true;
  soumissionEnCours = false;
  erreur = '';
  succes = '';

  ngOnInit(): void {
    this.tacheForm = this.fb.group({
      annonceId: ['', [Validators.required]],
      prestataireId: ['', [Validators.required]],
      titre: ['', [Validators.required]],
      description: [''],
      dateIntervention: ['', [Validators.required]],
      statut: [StatutTache.A_FAIRE, [Validators.required]]
    });

    this.chargerTaches();
  }

  chargerTaches(): void {
    const user = this.authService.getUtilisateurConnecte();
    if (!user) return;

    this.chargement = true;
    this.tacheService.obtenirTaches(user.id).subscribe({
      next: (data) => {
        this.taches = data;
        this.chargement = false;
      },
      error: () => {
        this.chargement = false;
        this.erreur = 'Erreur lors du chargement des tâches.';
      }
    });
  }

  assignerTache(): void {
    if (this.tacheForm.invalid) return;

    this.soumissionEnCours = true;
    this.erreur = '';
    this.succes = '';

    this.tacheService.assignerTache(this.tacheForm.value).subscribe({
      next: () => {
        this.soumissionEnCours = false;
        this.succes = 'Tâche assignée avec succès !';
        this.tacheForm.reset({ statut: StatutTache.A_FAIRE });
        this.chargerTaches();
      },
      error: (err) => {
        this.soumissionEnCours = false;
        this.erreur = err.error?.message || 'Erreur lors de l\'assignation.';
      }
    });
  }

  changerStatut(tache: TachePrestataireDTO, nouveauStatut: StatutTache): void {
    if (!tache.id) return;

    const payload: TachePrestataireDTO = {
      ...tache,
      statut: nouveauStatut
    };

    this.tacheService.mettreAJourStatut(tache.id, payload).subscribe({
      next: () => this.chargerTaches(),
      error: () => alert('Erreur lors du changement de statut')
    });
  }

  getBadgeClass(statut: StatutTache): string {
    switch (statut) {
      case StatutTache.A_FAIRE: return 'bg-warning text-dark';
      case StatutTache.EN_COURS: return 'bg-info text-dark';
      case StatutTache.TERMINEE: return 'bg-success';
      case StatutTache.ANNULEE: return 'bg-danger';
      default: return 'bg-secondary';
    }
  }
}
