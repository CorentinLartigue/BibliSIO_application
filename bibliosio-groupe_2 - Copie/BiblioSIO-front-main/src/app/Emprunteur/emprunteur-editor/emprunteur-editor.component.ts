import {Component} from '@angular/core';
import {Emprunteur} from "../Emprunteur";
import {EmprunteurService} from "../emprunteur.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-emprunteur-editor',
  templateUrl: './emprunteur-editor.component.html',
  styleUrls: ['./emprunteur-editor.component.css']
})
export class EmprunteurEditorComponent {

  emprunteur: Emprunteur = {} as Emprunteur
  updating: boolean = false
  creating: boolean = false

  constructor(
      private emprunteurService: EmprunteurService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.emprunteur!=null){
      this.emprunteur=history.state.emprunteur
    }
    this.creating = history.state.creating
    this.updating = history.state.updating
  }

  edit() {
    if(this.updating) {
      this.emprunteurService.update(this.emprunteur)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.emprunteurService.create(this.emprunteur)
          .subscribe((location)=>this.processCreate(location))
    }
  }

  processUpdate() {
    this.router.navigate(["/emprunteurs/"+this.emprunteur.id],
      {state: {emprunteur: this.emprunteur}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
