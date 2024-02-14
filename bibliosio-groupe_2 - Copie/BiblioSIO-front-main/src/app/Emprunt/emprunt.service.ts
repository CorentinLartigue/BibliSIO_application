import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Emprunt} from "./Emprunt";

@Injectable({
  providedIn: 'root'
})
export class EmpruntService extends GenericService<Emprunt>{

  protected override className = "Emprunt"
  protected override url = "http://localhost:8080/emprunts"

}
