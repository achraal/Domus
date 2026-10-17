import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // 1. Vérifier si l'utilisateur est connecté
  if (!authService.estConnecte()) {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  // 2. Récupérer les rôles autorisés définis sur la route
  const rolesAutorises = route.data['roles'] as Array<string>;

  if (!rolesAutorises || rolesAutorises.length === 0) {
    return true; // Si aucun rôle spécifié, l'accès est accordé aux utilisateurs connectés
  }

  // 3. Vérifier si l'utilisateur possède au moins un des rôles requis
  const aAcces = rolesAutorises.some(role => authService.aLeRole(role));

  if (aAcces) {
    return true;
  }

  // 4. Redirection en cas d'accès refusé (ex: vers la liste des annonces ou une page 403)
  router.navigate(['/annonces']);
  return false;
};
