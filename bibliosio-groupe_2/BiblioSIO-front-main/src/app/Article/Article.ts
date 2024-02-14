import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Article {
  id: Number,
  titre: String,
  description: String,
  exemplaire: SerializedUrlObject
}
