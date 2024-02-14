import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Emprunt {
  id: Number,
  emprunteur: SerializedUrlObject,
  exemplaire: SerializedUrlObject,
  dateEmprunt: Date,
  dateEcheance: Date,
  dateRetour: Date,
  statut: StatutEmprunt
}

export enum StatutEmprunt {
  EN_COURS = "EN_COURS",
  EN_RETARD = "EN_RETARD",
  TERMINE = "TERMINE"
}
