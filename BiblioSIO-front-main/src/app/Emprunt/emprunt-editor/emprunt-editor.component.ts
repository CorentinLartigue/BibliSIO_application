import {Component, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {Emprunt, StatutEmprunt} from "../Emprunt";
import {EmpruntService} from "../emprunt.service";
import {Emprunteur} from "../../Emprunteur/Emprunteur";
import {Exemplaire} from "../../Exemplaire/Exemplaire";
import {EmprunteurService} from "../../Emprunteur/emprunteur.service";
import {ExemplaireService} from "../../Exemplaire/exemplaire.service";
import {Observable} from "rxjs";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-emprunt-editor',
  templateUrl: './emprunt-editor.component.html',
  styleUrls: ['./emprunt-editor.component.css']
})
export class EmpruntEditorComponent {
  emprunt: Emprunt = {} as Emprunt
  updating: boolean = false
  creating: boolean = false
  emprunteurs$!: Observable<Emprunteur[]>
  exemplaires$!: Observable<Exemplaire[]>
  statuts = Object.values(StatutEmprunt)
  @ViewChild('myForm') myForm!: NgForm;

  constructor(
      private empruntService: EmpruntService,
      private emprunteurService: EmprunteurService,
      private exemplaireService: ExemplaireService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.emprunt!=null){
      this.emprunt=history.state.emprunt
    }
    this.creating = history.state.creating
    this.updating = history.state.updating
    this.emprunteurs$=this.emprunteurService.get()
    this.exemplaires$=this.exemplaireService.get()
  }

  edit() {
    if(this.updating) {
      console.log(this.myForm.value)
      console.log(this.emprunt)
      this.empruntService.update(this.emprunt)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.empruntService.create(this.emprunt)
          .subscribe((location)=>this.processCreate(location))
    }
  }

  processUpdate() {
    this.router.navigate(["/emprunts/"+this.emprunt.id],
      {state: {emprunt: this.emprunt}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
