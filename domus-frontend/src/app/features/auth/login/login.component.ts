import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', [Validators.required, Validators.minLength(4)]]
  });

  errorMessage = '';
  isLoading = false;

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      this.authService.login(this.loginForm.value).subscribe({
        next: (res) => {
          this.isLoading = false;
          // Redirection après connexion réussie (à adapter plus tard selon le rôle)
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.isLoading = false;
          // Gestion des erreurs (401 Unauthorized, 403, etc.)
          if (err.status === 401 || err.status === 403) {
            this.errorMessage = 'Email ou mot de passe incorrect.';
          } else {
            this.errorMessage = 'Une erreur est survenue lors de la connexion.';
          }
          console.error(err);
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }
}
