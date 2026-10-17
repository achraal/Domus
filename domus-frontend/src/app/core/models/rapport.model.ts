export enum TypeRapport {
  REVENUS = 'REVENUS',
  RESERVATIONS = 'RESERVATIONS',
  TAUX_OCCUPATION = 'TAUX_OCCUPATION',
  PERFORMANCE_GLOBALE = 'PERFORMANCE_GLOBALE'
}

export interface RapportDTO {
  id?: number;
  titre: string;
  typeRapport: TypeRapport;
  dateDebut: string;
  dateFin: string;
  dateGeneration?: string;
  revenuTotal?: number;
  totalReservations?: number;
  tauxOccupation?: number;
  contenuJson?: string;
}
