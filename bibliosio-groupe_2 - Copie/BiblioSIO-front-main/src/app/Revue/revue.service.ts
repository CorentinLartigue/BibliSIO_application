import {Injectable} from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Revue} from "./Revue";

@Injectable({
  providedIn: 'root'
})
export class RevueService extends GenericService<Revue> {
  protected override className = "Revue"
  protected override url = "http://localhost:8080/revues"
}
