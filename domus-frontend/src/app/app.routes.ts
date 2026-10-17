// src/app/app.routes.ts
import { Routes } from '@angular/router';

// Authentification
import { LoginComponent } from './features/auth/login/login.component';
import { SignupComponent } from './features/auth/signup/signup.component';

// Admin
import { UserListComponent } from './features/admin/user-list/user-list.component';
import { RapportListComponent } from './features/rapports/pages/rapport-list/rapport-list.component';

// Annonces, États & Tarification
import { AnnonceListComponent } from './features/annonces/annonce-list/annonce-list.component';
import { AnnonceFormComponent } from './features/annonces/annonce-form/annonce-form.component';
import { SuiviEtatComponent } from './features/etats/pages/suivi-etat/suivi-etat.component';
import { GestionTarificationComponent } from './features/tarifications/pages/gestion-tarification/gestion-tarification.component';
// Réservations & Paiements
import { ReservationLocataireComponent } from './features/reservations/pages/reservation-locataire/reservation-locataire.component';
import { ReservationFormComponent } from './features/reservations/pages/reservation-form/reservation-form.component';
import { ReservationProprietaireComponent } from './features/reservations/pages/reservation-proprietaire/reservation-proprietaire.component';
import { GestionPaiementComponent } from './features/paiements/pages/gestion-paiement/gestion-paiement.component';
// Tâches Prestataires & Communication
import { TacheListComponent } from './features/taches/pages/tache-list/tache-list.component';
import { MessagerieComponent } from './features/communication/pages/messagerie/messagerie.component';
// Guards
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  // 1. AUTHENTIFICATION (Accès public)
  { path: 'login', component: LoginComponent },
  { path: 'inscription', component: SignupComponent },
  // 2. ADMINISTRATION
  {
    path: 'admin/utilisateurs',
    component: UserListComponent,
    canActivate: [roleGuard],
    data: { roles: ['ADMINISTRATEUR'] }
  },
  {
    path: 'admin/rapports',
    component: RapportListComponent,
    canActivate: [roleGuard],
    data: { roles: ['ADMINISTRATEUR'] }
  },
  // 3. ANNONCES & SUIVIS ASSOCIÉS
  { path: 'annonces', component: AnnonceListComponent },
  {
    path: 'annonces/nouvelle',
    component: AnnonceFormComponent,
    canActivate: [roleGuard],
    data: { roles: ['PROPRIETAIRE'] }
  },
  {
    path: 'annonces/editer/:id',
    component: AnnonceFormComponent,
    canActivate: [roleGuard],
    data: { roles: ['PROPRIETAIRE'] }
  },
  {
    path: 'annonces/:idAnnonce/etats',
    component: SuiviEtatComponent
  },
  {
    path: 'annonces/:idAnnonce/tarifications',
    component: GestionTarificationComponent,
    canActivate: [roleGuard],
    data: { roles: ['PROPRIETAIRE', 'ADMINISTRATEUR'] }
  },
  // Route pour la consultation d'une annonce spécifique (référencée par les boutons "Voir")
  {
    path: 'annonces/:id',
    component: AnnonceFormComponent // ou un AnnonceDetailComponent dédié
  },
  // 4. RÉSERVATIONS & Paiements
  {
    path: 'reservations/mes-reservations',
    component: ReservationLocataireComponent,
    canActivate: [roleGuard],
    data: { roles: ['LOCATAIRE'] }
  },
  {
    path: 'reservations/nouvelle/:annonceId',
    component: ReservationFormComponent,
    canActivate: [roleGuard],
    data: { roles: ['LOCATAIRE'] }
  },
  {
    path: 'reservations/annonce/:idAnnonce',
    component: ReservationProprietaireComponent,
    canActivate: [roleGuard],
    data: { roles: ['PROPRIETAIRE', 'ADMINISTRATEUR'] }
  },
  {
    path: 'reservations/:idReservation/paiements',
    component: GestionPaiementComponent,
    canActivate: [roleGuard],
    data: { roles: ['LOCATAIRE', 'PROPRIETAIRE', 'ADMINISTRATEUR'] }
  },
  // 5. INTERVENTIONS & MESSAGERIE
  {
    path: 'taches',
    component: TacheListComponent,
    canActivate: [roleGuard],
    data: { roles: ['PROPRIETAIRE', 'PRESTATAIRE', 'ADMINISTRATEUR'] }
  },
  {
    path: 'communication',
    component: MessagerieComponent,
    canActivate: [roleGuard],
    data: { roles: ['LOCATAIRE', 'PROPRIETAIRE', 'PRESTATAIRE', 'ADMINISTRATEUR'] }
  },
  // 6. REDIRECTIONS PAR DÉFAUT & CATCH-ALL
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
