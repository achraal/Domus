import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../../../../core/services/reservation.service';

@Component({
  selector: 'app-reservation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reservation-form.component.html'
})
export class ReservationFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private reservationService = inject(ReservationService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  reservationForm!: FormGroup;
  annonceId!: number;
  soumissionEnCours = false;
  erreur = '';

  ngOnInit(): void {
    this.annonceId = Number(this.route.snapshot.paramMap.get('annonceId'));

    this.reservationForm = this.fb.group({
      dateDebut: ['', [Validators.required]],
      dateFin: ['', [Validators.required]],
      remarques: ['']
    });
  }
  annuler(): void {
    this.router.navigate(['/annonces']);
  }

  soumettre(): void {
    if (this.reservationForm.invalid) return;

    this.soumissionEnCours = true;
    this.erreur = '';

    const payload = {
      annonceId: this.annonceId,
      ...this.reservationForm.value
    };

    this.reservationService.creerReservation(payload).subscribe({
      next: () => {
        this.soumissionEnCours = false;
        this.router.navigate(['/reservations/mes-reservations']);
      },
      error: (err) => {
        this.soumissionEnCours = false;
        this.erreur = err.error?.message || 'Erreur lors de la création de la réservation.';
      }
    });
  }
}
