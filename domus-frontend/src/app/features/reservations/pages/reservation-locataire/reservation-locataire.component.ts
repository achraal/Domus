import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ReservationService } from '../../../../core/services/reservation.service';
import { ReservationResponseDTO, StatutReservation } from '../../../../core/models/reservation.model';

@Component({
  selector: 'app-reservation-locataire',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './reservation-locataire.component.html'
})
export class ReservationLocataireComponent implements OnInit {
  private reservationService = inject(ReservationService);

  reservations: ReservationResponseDTO[] = [];
  chargement = true;
  erreur = '';
  statuts = StatutReservation;

  ngOnInit(): void {
    this.chargerMesReservations();
  }

  chargerMesReservations(): void {
    this.chargement = true;
    this.reservationService.obtenirMesReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Impossible de charger vos réservations.';
        this.chargement = false;
      }
    });
  }

  annuler(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir annuler cette réservation ?')) {
      this.reservationService.annulerReservation(id).subscribe({
        next: () => {
          // Rechargement ou mise à jour locale du statut
          this.chargerMesReservations();
        },
        error: (err) => alert('Erreur lors de l\'annulation de la réservation.')
      });
    }
  }

  getBadgeClass(statut: StatutReservation): string {
    switch (statut) {
      case StatutReservation.CONFIRMEE: return 'bg-success';
      case StatutReservation.EN_ATTENTE: return 'bg-warning text-dark';
      case StatutReservation.REFUSEE: return 'bg-danger';
      case StatutReservation.ANNULEE: return 'bg-secondary';
      default: return 'bg-info';
    }
  }
}
