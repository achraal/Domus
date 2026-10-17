import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AnnonceService } from '../../../core/services/annonce.service';

@Component({
  selector: 'app-annonce-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './annonce-form.component.html'
})
export class AnnonceFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private annonceService = inject(AnnonceService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  annonceForm!: FormGroup;
  estModeEdition = false;
  annonceId?: number;

  ngOnInit(): void {
    this.annonceForm = this.fb.group({
      titre: ['', [Validators.required]],
      description: ['', [Validators.required]],
      prix: [0, [Validators.required, Validators.min(0)]],
      adresse: ['']
    });

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.estModeEdition = true;
      this.annonceId = +idParam;
      // GET /api/annonces/{id}
      this.annonceService.obtenirAnnonceParId(this.annonceId).subscribe({
        next: (annonce) => this.annonceForm.patchValue(annonce),
        error: () => this.router.navigate(['/annonces'])
      });
    }
  }

  soumettre(): void {
    if (this.annonceForm.invalid) return;

    const requestData = this.annonceForm.value;

    if (this.estModeEdition && this.annonceId) {
      // PUT /api/annonces/{id}
      this.annonceService.modifierAnnonce(this.annonceId, requestData).subscribe({
        next: () => this.router.navigate(['/annonces']),
        error: (err) => console.error('Erreur de modification', err)
      });
    } else {
      // POST /api/annonces
      this.annonceService.creerAnnonce(requestData).subscribe({
        next: () => this.router.navigate(['/annonces']),
        error: (err) => console.error('Erreur de création', err)
      });
    }
  }
}
