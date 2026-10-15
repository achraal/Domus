import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  // On récupère le token stocké après la connexion
  const token = localStorage.getItem('token');

  // Si le token existe, on clone la requête pour y injecter l'en-tête Authorization
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }

  // Sinon, on laisse passer la requête telle quelle (ex: pour le login/inscription)
  return next(req);
};
