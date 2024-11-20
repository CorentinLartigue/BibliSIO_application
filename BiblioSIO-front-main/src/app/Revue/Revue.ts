import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Revue {
  id: Number,
  titre: String,
  exemplaires: SerializedUrlObject[]
}
