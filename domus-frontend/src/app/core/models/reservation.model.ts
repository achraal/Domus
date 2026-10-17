export enum StatutReservation {
  EN_ATTENTE = 'EN_ATTENTE',
  CONFIRMEE = 'CONFIRMEE',
  REFUSEE = 'REFUSEE',
  ANNULEE = 'ANNULEE'
}

export interface ReservationRequestDTO {
  annonceId: number;
  dateDebut: string; // Format ISO (YYYY-MM-DD ou YYYY-MM-DDTHH:mm:ss)
  dateFin: string;
  remarques?: string;
}

export interface ReservationResponseDTO {
  id: number;
  annonceId: number;
  titreAnnonce?: string;
  locataireNom?: string;
  dateDebut: string;
  dateFin: string;
  prixTotal?: number;
  statut: StatutReservation;
  dateCreation?: string;
}
