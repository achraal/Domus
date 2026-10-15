# 🎨 DOMUS Frontend — Client Web Angular 22 & Tailwind CSS

> **Interface utilisateur moderne et responsive du système DOMUS**, développée avec Angular 22 (Standalone Components) et stylisée avec Tailwind CSS. Elle intègre une gestion sécurisée de session par JWT, un intercepteur HTTP fonctionnel et une architecture réactive basée sur RxJS.

[![Angular](https://img.shields.io/badge/Angular-22-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-6.0-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![RxJS](https://img.shields.io/badge/RxJS-7.8-B7178C?style=for-the-badge&logo=reactivex&logoColor=white)](https://rxjs.dev/)
[![Vitest](https://img.shields.io/badge/Vitest-5.0-6E9F18?style=for-the-badge&logo=vitest&logoColor=white)](https://vitest.dev/)

---

## 📌 Sommaire
- [Vue d'ensemble](#-vue-densemble)
- [Fonctionnalités Clés du Client](#-fonctionnalités-clés-du-client)
- [Architecture & Organisation du Projet](#-architecture--organisation-du-projet)
- [Sécurité & Intercepteur JWT](#-sécurité--intercepteur-jwt)
- [Configuration des Environnements](#-configuration-des-environnements)
- [Installation & Démarrage](#-installation--démarrage)
- [Scripts Disponibles](#-scripts-disponibles)
- [Design System & Tailwind CSS](#-design-system--tailwind-css)
- [Connexion avec le Backend Spring Boot](#-connexion-avec-le-backend-spring-boot)

---

## 🌟 Vue d'ensemble

L'application frontend **DOMUS** offre une interface utilisateur élégante, intuitive et dynamique permettant aux différents acteurs (locataires, propriétaires, prestataires et administrateurs) d'interagir facilement avec la plateforme :
- **Catalogue interactif d'appartements** avec recherche dynamique, galerie d'images et détails des équipements.
- **Espace Réservation** avec sélection des dates de séjour et calcul automatisé des coûts selon la saison.
- **Espace Propriétaire** pour publier des biens, gérer les disponibilités, suivre les loyers et assigner des missions.
- **Espace Prestataire** pour consulter le carnet d'interventions et mettre à jour leur progression.
- **Messagerie & Notifications** pour des échanges directs et fluides entre utilisateurs.

---

## 🚀 Fonctionnalités Clés du Client

| Fonctionnalité | Description |
| :--- | :--- |
| **🔐 Authentification JWT** | Formulaires de connexion et d'inscription avec stockage sécurisé du token et profil dans le `localStorage`. |
| **🛡️ Intercepteur HTTP Réactif** | Injection automatique et transparente de l'en-tête `Authorization: Bearer <token>` sur chaque appel API protégé. |
| **⚡ Standalone Components** | Architecture Angular moderne sans `NgModule`, garantissant des temps de chargement ultra-rapides et un découpage optimal. |
| **📱 Responsive Design** | Interface fluide et adaptée aux smartphones, tablettes et écrans larges grâce à l'écosystème utilitaire Tailwind CSS. |
| **🔄 Gestion d'État Réactive** | Utilisation avancée d'observables RxJS et signaux pour la synchronisation en temps réel des données. |

---

## 🏛️ Architecture & Organisation du Projet

Le code source est structuré selon les standards modernes d'Angular :

```
domus-frontend/
├── src/
│   ├── app/
│   │   ├── core/                        # Singleton services, intercepteurs, modèles transverses
│   │   │   ├── interceptors/
│   │   │   │   ├── jwt-interceptor.ts   # Intercepteur HTTP fonctionnel pour le jeton Bearer
│   │   │   │   └── jwt-interceptor.spec.ts
│   │   │   ├── models/
│   │   │   │   └── auth.model.ts        # Interfaces TypeScript (DTOs Auth, Utilisateur)
│   │   │   └── services/
│   │   │       ├── auth.service.ts      # Gestion de connexion, déconnexion et état utilisateur
│   │   │       └── auth.spec.ts
│   │   ├── app.config.ts                # Configuration Angular (Providers, HttpClient, Routing)
│   │   ├── app.routes.ts                # Définition des routes de l'application
│   │   ├── app.html                     # Template racine
│   │   ├── app.css                      # Styles du composant racine
│   │   └── app.ts                       # Composant racine AppComponent
│   │
│   ├── environments/
│   │   ├── environment.ts               # Configuration locale / développement (API: localhost:8080)
│   │   └── environment.development.ts  # Environnement de développement actif
│   │
│   ├── index.html                       # Fichier HTML principal
│   ├── main.ts                          # Bootstrap de l'application Angular
│   └── styles.css                       # Directives globales Tailwind (@tailwind base, components, utilities)
│
├── angular.json                         # Configuration du build Angular CLI
├── package.json                         # Dépendances npm et scripts
├── tailwind.config.js                   # Thème et configuration Tailwind CSS
└── tsconfig.json                        # Paramètres du compilateur TypeScript
```

---

## 🔒 Sécurité & Intercepteur JWT

L'application utilise un intercepteur fonctionnel Angular (`HttpInterceptorFn`) qui intercepte automatiquement chaque requête sortante :

```typescript
// src/app/core/interceptors/jwt-interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }

  return next(req);
};
```

Cet intercepteur est enregistré globalement dans `app.config.ts` via `provideHttpClient(withInterceptors([jwtInterceptor]))`.

---

## 🌐 Configuration des Environnements

Le point d'accès à l'API backend est centralisé dans le dossier `src/environments/` :

```typescript
// src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

Pour cibler un serveur distant ou un environnement de staging, modifiez l'URL de l'API dans `environment.development.ts` ou configurez un fichier de production.

---

## 💻 Installation & Démarrage

### 1. Prérequis
- [Node.js](https://nodejs.org/) (version 20 LTS ou supérieure recommandée)
- [npm](https://www.npmjs.com/) (version 10+ ou 11+)

### 2. Installation des dépendances
Placez-vous dans le répertoire `domus-frontend` :
```bash
cd domus-frontend
npm install
```

### 3. Lancer le serveur de développement
```bash
npm start
# ou : npx ng serve
```
Accédez à l'application sur **`http://localhost:4200/`**. L'application se recharge automatiquement dès que vous modifiez un fichier source.

---

## 📜 Scripts Disponibles

Dans le fichier `package.json`, plusieurs commandes pratiques sont à votre disposition :

| Commande | Action |
| :--- | :--- |
| `npm start` | Lance le serveur de développement local sur le port 4200. |
| `npm run build` | Compile l'application pour la production dans le dossier `dist/`. |
| `npm run watch` | Compile et écoute les modifications en mode développement. |
| `npm test` | Exécute les tests unitaires avec le test runner **Vitest**. |

---

## 🎨 Design System & Tailwind CSS

Le projet utilise **Tailwind CSS 3.4** pour une personnalisation visuelle poussée et sans friction :
- Les directives fondamentales sont déclarées dans `src/styles.css` :
  ```css
  @tailwind base;
  @tailwind components;
  @tailwind utilities;
  ```
- Les composants exploitent des palettes harmonieuses, des états interactifs fluides (`hover:`, `focus:`, `transition-all`), et des bordures soignées pour un rendu professionnel et épuré.

---

## 🔗 Connexion avec le Backend Spring Boot

Pour que le frontend puisse communiquer avec l'API Spring Boot :
1. Assurez-vous que le backend `domus-project` est démarré sur le port **8080**.
2. Vérifiez que la base PostgreSQL est accessible et initialisée.
3. Le service d'authentification (`AuthService`) envoie les requêtes vers :
   - `POST http://localhost:8080/api/auth/login`
   - `POST http://localhost:8080/api/auth/inscription`
4. Une fois connecté, le token JWT reçu est automatiquement joint à tous les appels subséquents (`/api/annonces`, `/api/reservations`, etc.).
