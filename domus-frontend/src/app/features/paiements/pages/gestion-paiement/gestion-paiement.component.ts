import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PaiementService } from '../../../../core/services/paiement.service';
import { ModePaiement, PaiementDTO, QuittanceDTO, StatutPaiement, TypePaiement } from '../../../../core/models/paiement.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-gestion-paiement',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './gestion-paiement.component.html'
})
export class GestionPaiementComponent implements OnInit {
  private fb = inject(FormBuilder);
  private paiementService = inject(PaiementService);
  private route = inject(ActivatedRoute);
  public authService = inject(AuthService);

  idReservation!: number;
  paiements: PaiementDTO[] = [];
  paiementForm!: FormGroup;

  typesPaiement = Object.values(TypePaiement);
  modesPaiement = Object.values(ModePaiement);

  quittanceSelectionnee: QuittanceDTO | null = null;
  chargement = true;
  soumissionEnCours = false;
  remboursementEnCours = false;
  erreur = '';
  succes = '';

  ngOnInit(): void {
    this.idReservation = Number(this.route.snapshot.paramMap.get('idReservation'));

    this.paiementForm = this.fb.group({
      montant: [0, [Validators.required, Validators.min(1)]],
      typePaiement: [TypePaiement.LOYER, [Validators.required]],
      modePaiement: [ModePaiement.CARTE_BANCAIRE, [Validators.required]],
      remarques: ['']
    });

    this.chargerPaiements();
  }

  chargerPaiements(): void {
    this.chargement = true;
    this.paiementService.obtenirPaiementsParReservation(this.idReservation).subscribe({
      next: (data) => {
        this.paiements = data;
        this.chargement = false;
      },
      error: () => {
        this.chargement = false;
        this.erreur = 'Erreur lors du chargement des paiements.';
      }
    });
  }

  effectuerPaiement(): void {
    if (this.paiementForm.invalid) return;

    this.soumissionEnCours = true;
    this.erreur = '';
    this.succes = '';

    const payload: PaiementDTO = {
      reservationId: this.idReservation,
      ...this.paiementForm.value
    };

    this.paiementService.effectuerPaiement(payload).subscribe({
      next: () => {
        this.soumissionEnCours = false;
        this.succes = 'Paiement effectué avec succès !';
        this.paiementForm.reset({
          montant: 0,
          typePaiement: TypePaiement.LOYER,
          modePaiement: ModePaiement.CARTE_BANCAIRE
        });
        this.chargerPaiements();
      },
      error: (err) => {
        this.soumissionEnCours = false;
        this.erreur = err.error?.message || 'Échec du traitement du paiement.';
      }
    });
  }

  telechargerQuittance(idPaiement: number): void {
    this.paiementService.obtenirQuittanceParPaiement(idPaiement).subscribe({
      next: (quittance) => {
        this.quittanceSelectionnee = quittance;
      },
      error: () => alert('Impossible de récupérer la quittance.')
    });
  }

  rembourserDepot(): void {
    if (confirm('Voulez-vous procéder au remboursement du dépôt de garantie pour cette réservation ?')) {
      this.remboursementEnCours = true;
      this.paiementService.rembourserDepot(this.idReservation).subscribe({
        next: () => {
          this.remboursementEnCours = false;
          this.succes = 'Dépôt de garantie remboursé avec succès.';
          this.chargerPaiements();
        },
        error: (err) => {
          this.remboursementEnCours = false;
          alert(err.error?.message || 'Erreur lors du remboursement du dépôt.');
        }
      });
    }
  }

  getStatutBadgeClass(statut?: StatutPaiement): string {
    switch (statut) {
      case StatutPaiement.REUSSI: return 'bg-success';
      case StatutPaiement.ECHOUE: return 'bg-danger';
      case StatutPaiement.EN_ATTENTE: return 'bg-warning text-dark';
      case StatutPaiement.REMBOURSE: return 'bg-info text-dark';
      default: return 'bg-secondary';
    }
  }
}
