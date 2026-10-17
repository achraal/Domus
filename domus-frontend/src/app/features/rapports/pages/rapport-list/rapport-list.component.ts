import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RapportService } from '../../../../core/services/rapport.service';
import { RapportDTO, TypeRapport } from '../../../../core/models/rapport.model';

@Component({
  selector: 'app-rapport-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './rapport-list.component.html'
})
export class RapportListComponent implements OnInit {
  private fb = inject(FormBuilder);
  private rapportService = inject(RapportService);

  rapports: RapportDTO[] = [];
  rapportForm!: FormGroup;
  typesRapport = Object.values(TypeRapport);

  chargement = true;
  generationEnCours = false;
  erreur = '';
  succes = '';

  ngOnInit(): void {
    this.rapportForm = this.fb.group({
      titre: ['', [Validators.required]],
      typeRapport: [TypeRapport.REVENUS, [Validators.required]],
      dateDebut: ['', [Validators.required]],
      dateFin: ['', [Validators.required]]
    });

    this.chargerRapports();
  }

  chargerRapports(): void {
    this.chargement = true;
    this.rapportService.obtenirRapports().subscribe({
      next: (data) => {
        this.rapports = data;
        this.chargement = false;
      },
      error: () => {
        this.chargement = false;
        this.erreur = 'Erreur lors du chargement des rapports.';
      }
    });
  }

  generer(): void {
    if (this.rapportForm.invalid) return;

    this.generationEnCours = true;
    this.erreur = '';
    this.succes = '';

    this.rapportService.genererRapport(this.rapportForm.value).subscribe({
      next: () => {
        this.generationEnCours = false;
        this.succes = 'Rapport généré avec succès !';
        this.rapportForm.reset({ typeRapport: TypeRapport.REVENUS });
        this.chargerRapports();
      },
      error: (err) => {
        this.generationEnCours = false;
        this.erreur = err.error?.message || 'Erreur lors de la génération du rapport.';
      }
    });
  }
}
