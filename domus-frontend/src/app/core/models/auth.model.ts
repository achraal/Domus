export interface AuthRequestDTO {
  email: string;
  motDePasse: string;
}

export interface UtilisateurResponseDTO {
  idUtilisateur: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  adresse: string;
  typeUtilisateur: string;
}

export interface AuthResponseDTO {
  token: string;
  utilisateur: UtilisateurResponseDTO;
}
