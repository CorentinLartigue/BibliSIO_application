import {Component} from '@angular/core';
import {EmprunteurService} from "../emprunteur.service";
import {Emprunteur} from "../Emprunteur";
import {Router} from "@angular/router";

@Component({
  selector: 'app-emprunteur-list',
  templateUrl: './emprunteur-list.component.html',
  styleUrls: ['./emprunteur-list.component.css']
})
export class EmprunteurListComponent {

  emprunteurs: Emprunteur[]

  constructor(
    private emprunteurService: EmprunteurService,
    private router: Router
  ) {
    this.emprunteurs = []
    emprunteurService.get()
      .subscribe((emprunteurs)=>this.emprunteurs=emprunteurs)
  }

  openEmprunteurDetails(emprunteur: Emprunteur) {
    this.router.navigate(['/emprunteurs/'+emprunteur.id],
        {state: {emprunteur: emprunteur, solo: true}})
  }

  openCreateEmprunteur() {
    this.router.navigate(['/emprunteurs/edit'],
        {state: {creating: true}})
  }
}
