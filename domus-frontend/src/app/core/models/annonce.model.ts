export interface AnnonceRequestDTO {
  titre: string;
  description: string;
  prix: number;
  adresse?: string;
  // Ajoutez ici les autres champs requis par votre DTO backend
}

export interface AnnonceResponseDTO {
  id: number;
  titre: string;
  description: string;
  prix: number;
  adresse?: string;
  dateCreation?: string;
  // Ajoutez les propriétés retournées par le backend (ex: nom du propriétaire)
}
