import {Injectable} from '@angular/core';
import {Emprunteur} from "./Emprunteur";
import {GenericService} from "../utils/generic.service";

@Injectable({
  providedIn: 'root'
})
export class EmprunteurService extends GenericService<Emprunteur>{

  protected override url = "http://localhost:8080/emprunteurs"
  protected override className = "Emprunteur"

}
