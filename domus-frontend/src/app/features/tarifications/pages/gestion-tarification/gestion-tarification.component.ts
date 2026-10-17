// src/app/features/tarifications/pages/gestion-tarification/gestion-tarification.component.ts

import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TarificationService } from '../../../../core/services/tarification.service';
import { TarificationDTO, TypeTarification } from '../../../../core/models/tarification.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-gestion-tarification',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './gestion-tarification.component.html'
})
export class GestionTarificationComponent implements OnInit {
  private fb = inject(FormBuilder);
  private tarificationService = inject(TarificationService);
  private route = inject(ActivatedRoute);
  public authService = inject(AuthService);

  idAnnonce!: number;
  tarifications: TarificationDTO[] = [];
  tarificationForm!: FormGroup;
  calculForm!: FormGroup;

  typesTarification = Object.values(TypeTarification);
  chargement = true;
  soumissionEnCours = false;
  prixCalcule: number | null = null;
  calculEnCours = false;
  erreur = '';
  succes = '';

  ngOnInit(): void {
    this.idAnnonce = Number(this.route.snapshot.paramMap.get('idAnnonce'));

    this.tarificationForm = this.fb.group({
      nom: ['', [Validators.required]],
      typeTarification: [TypeTarification.SAISON, [Validators.required]],
      dateDebut: ['', [Validators.required]],
      dateFin: ['', [Validators.required]],
      pourcentage: [null],
      prixFixe: [null],
      description: ['']
    });

    this.calculForm = this.fb.group({
      dateTest: [new Date().toISOString().substring(0, 10), [Validators.required]]
    });

    this.chargerTarifications();
  }

  chargerTarifications(): void {
    this.chargement = true;
    this.tarificationService.obtenirTarifications(this.idAnnonce).subscribe({
      next: (data) => {
        this.tarifications = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Erreur lors du chargement des tarifications';
        this.chargement = false;
      }
    });
  }

  ajouterTarification(): void {
    if (this.tarificationForm.invalid) return;

    this.soumissionEnCours = true;
    this.erreur = '';
    this.succes = '';

    const payload: TarificationDTO = {
      annonceId: this.idAnnonce,
      ...this.tarificationForm.value
    };

    this.tarificationService.ajouterTarification(payload).subscribe({
      next: () => {
        this.soumissionEnCours = false;
        this.succes = 'Règle de tarification ajoutée avec succès !';
        this.tarificationForm.reset({ typeTarification: TypeTarification.SAISON });
        this.chargerTarifications();
      },
      error: (err) => {
        this.soumissionEnCours = false;
        this.erreur = err.error?.message || 'Erreur lors de l\'ajout de la tarification.';
      }
    });
  }

  supprimer(id: number): void {
    if (confirm('Voulez-vous vraiment supprimer cette règle tarifaire ?')) {
      this.tarificationService.supprimerTarification(id).subscribe({
        next: () => {
          this.tarifications = this.tarifications.filter(t => t.id !== id);
        },
        error: () => alert('Erreur lors de la suppression')
      });
    }
  }

  simulerPrix(): void {
    if (this.calculForm.invalid) return;

    this.calculEnCours = true;
    const dateSelected = this.calculForm.value.dateTest;

    this.tarificationService.calculerPrixPourDate(this.idAnnonce, dateSelected).subscribe({
      next: (prix) => {
        this.prixCalcule = prix;
        this.calculEnCours = false;
      },
      error: () => {
        this.calculEnCours = false;
        alert('Erreur lors du calcul du prix.');
      }
    });
  }

  getBadgeClass(type: TypeTarification): string {
    switch (type) {
      case TypeTarification.SAISON: return 'bg-primary';
      case TypeTarification.PROMOTION: return 'bg-success';
      case TypeTarification.EVENEMENT: return 'bg-warning text-dark';
      case TypeTarification.REMISE: return 'bg-info text-dark';
      case TypeTarification.SUPPLEMENT: return 'bg-danger';
      default: return 'bg-secondary';
    }
  }
}
