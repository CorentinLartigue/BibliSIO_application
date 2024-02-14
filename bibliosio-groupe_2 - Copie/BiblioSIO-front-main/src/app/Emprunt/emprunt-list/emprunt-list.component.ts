import { Component } from '@angular/core';
import {Emprunt} from "../../Emprunt/Emprunt";
import {EmpruntService} from "../../Emprunt/emprunt.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-emprunt-list',
  templateUrl: './emprunt-list.component.html',
  styleUrls: ['./emprunt-list.component.css']
})
export class EmpruntListComponent {
  emprunts: Emprunt[]

  constructor(
      private empruntService: EmpruntService,
      private router: Router
  ) {
    this.emprunts = []
    empruntService.get()
        .subscribe((emprunts)=>this.emprunts=emprunts)
  }

  openEmpruntDetails(emprunt: Emprunt) {
    this.router.navigate(['/emprunts/'+emprunt.id],
        {state: {emprunt: emprunt, solo: true}})
  }

  openCreateEmprunt() {
    this.router.navigate(['/emprunts/edit'],
        {state: {creating: true}})
  }
}
