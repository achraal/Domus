export enum StatutTache {
  A_FAIRE = 'A_FAIRE',
  EN_COURS = 'EN_COURS',
  TERMINEE = 'TERMINEE',
  ANNULEE = 'ANNULEE'
}

export interface TachePrestataireDTO {
  id?: number;
  annonceId: number;
  prestataireId: number;
  prestataireNom?: string;
  titre: string;
  description?: string;
  dateIntervention: string; // YYYY-MM-DD
  statut: StatutTache;
}
