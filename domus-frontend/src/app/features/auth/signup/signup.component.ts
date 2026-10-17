import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './signup.component.html'
})
export class SignupComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  signupForm: FormGroup = this.fb.group({
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', [Validators.required, Validators.minLength(6)]],
    telephone: ['', Validators.required],
    adresse: ['', Validators.required],
    typeUtilisateur: ['LOCATAIRE', Validators.required] // Par défaut
  });

  isLoading = false;
  errorMessage = '';

  onSubmit() {
    if (this.signupForm.valid) {
      this.isLoading = true;
      this.authService.inscription(this.signupForm.value).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/login']); // Redirection vers le login après succès
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = err.error?.message || 'Erreur lors de l\'inscription. Cet email est peut-être déjà utilisé.';
        }
      });
    } else {
      this.signupForm.markAllAsTouched();
    }
  }
}
