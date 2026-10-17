export interface MessageDTO {
  id?: number;
  expediteurId: number;
  expediteurNom?: string;
  destinataireId: number;
  destinataireNom?: string;
  contenu: string;
  dateEnvoi?: string; // ISO String
}

export interface NotificationDTO {
  id: number;
  utilisateurId: number;
  titre: string;
  message: string;
  lue: boolean;
  dateCreation: string;
  type?: string;
}
