import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Exemplaire} from "./Exemplaire";

@Injectable({
  providedIn: 'root'
})
export class ExemplaireService extends GenericService<Exemplaire> {
  protected override className = "Exemplaire"
  protected override url = "http://localhost:8080/exemplaires"
}
