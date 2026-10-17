export enum StatutPaiement {
  REUSSI = 'REUSSI',
  ECHOUE = 'ECHOUE',
  EN_ATTENTE = 'EN_ATTENTE',
  REMBOURSE = 'REMBOURSE'
}

export enum TypePaiement {
  LOYER = 'LOYER',
  DEPOT_GARANTIE = 'DEPOT_GARANTIE',
  REMBOURSEMENT = 'REMBOURSEMENT'
}

export enum ModePaiement {
  CARTE_BANCAIRE = 'CARTE_BANCAIRE',
  VIREMENT = 'VIREMENT',
  ESPECES = 'ESPECES'
}

export interface PaiementDTO {
  id?: number;
  reservationId: number;
  montant: number;
  statut?: StatutPaiement;
  typePaiement: TypePaiement;
  modePaiement: ModePaiement;
  datePaiement?: string;
  transactionId?: string;
  remarques?: string;
}

export interface QuittanceDTO {
  id: number;
  paiementId: number;
  numeroQuittance: string;
  nomPayeur: string;
  nomBeneficiaire: string;
  montant: number;
  dateEmission: string;
  periode: string;
  lienTelechargementPdf?: string;
}
