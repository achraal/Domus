import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ReservationService } from '../../../../core/services/reservation.service';
import { ReservationResponseDTO, StatutReservation } from '../../../../core/models/reservation.model';

@Component({
  selector: 'app-reservation-proprietaire',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './reservation-proprietaire.component.html'
})
export class ReservationProprietaireComponent implements OnInit {
  private reservationService = inject(ReservationService);
  private route = inject(ActivatedRoute);

  idAnnonce!: number;
  reservations: ReservationResponseDTO[] = [];
  chargement = true;
  statuts = StatutReservation;

  ngOnInit(): void {
    this.idAnnonce = Number(this.route.snapshot.paramMap.get('idAnnonce'));
    this.chargerReservations();
  }

  chargerReservations(): void {
    this.chargement = true;
    this.reservationService.obtenirReservationsParAnnonce(this.idAnnonce).subscribe({
      next: (data) => {
        this.reservations = data;
        this.chargement = false;
      },
      error: () => this.chargement = false
    });
  }

  changerStatut(id: number, statut: StatutReservation): void {
    this.reservationService.changerStatut(id, statut).subscribe({
      next: () => this.chargerReservations(),
      error: (err) => alert('Impossible de mettre à jour le statut')
    });
  }
}
