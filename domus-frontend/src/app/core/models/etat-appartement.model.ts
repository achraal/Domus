export enum StatutAppartement {
  LIBRE = 'LIBRE',
  OCCUPE = 'OCCUPE',
  MAINTENANCE = 'MAINTENANCE'
}

export interface EtatAppartementDTO {
  id?: number;
  annonceId: number;
  statut: StatutAppartement;
  commentaire?: string;
  dateChangement?: string; // Date au format ISO
  modifiePar?: string;
}
