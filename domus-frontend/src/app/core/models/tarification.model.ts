export enum TypeTarification {
  SAISON = 'SAISON',
  PROMOTION = 'PROMOTION',
  EVENEMENT = 'EVENEMENT',
  REMISE = 'REMISE',
  SUPPLEMENT = 'SUPPLEMENT'
}

export interface TarificationDTO {
  id?: number;
  annonceId: number;
  typeTarification: TypeTarification;
  nom: string;
  dateDebut: string; // Format YYYY-MM-DD
  dateFin: string;   // Format YYYY-MM-DD
  pourcentage?: number; // Ex: +15% (15) ou -10% (-10)
  prixFixe?: number;    // Prix fixe par nuit pour la période
  description?: string;
}
