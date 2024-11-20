import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Exemplaire {
  id: Number,
  revue: SerializedUrlObject,
  titre: String,
  moisParution: String,
  anneeParution: String,
  articles: SerializedUrlObject[],
  statut: Boolean
}
