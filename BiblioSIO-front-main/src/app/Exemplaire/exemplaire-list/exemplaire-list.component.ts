import { Component } from '@angular/core';
import {Exemplaire} from "../../Exemplaire/Exemplaire";
import {ExemplaireService} from "../../Exemplaire/exemplaire.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-exemplaire-list',
  templateUrl: './exemplaire-list.component.html',
  styleUrls: ['./exemplaire-list.component.css']
})
export class ExemplaireListComponent {
  exemplaires: Exemplaire[]

  constructor(
      private exemplaireService: ExemplaireService,
      private router: Router
  ) {
    this.exemplaires = []
    exemplaireService.get()
        .subscribe((exemplaires)=>this.exemplaires=exemplaires)
  }

  openExemplaireDetails(exemplaire: Exemplaire) {
    this.router.navigate(['/exemplaires/'+exemplaire.id],
        {state: {exemplaire: exemplaire, solo: true}})
  }

  openCreateExemplaire() {
    this.router.navigate(['/exemplaires/edit'],
        {state: {creating: true}})
  }
}
