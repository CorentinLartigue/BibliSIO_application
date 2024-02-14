import { Component } from '@angular/core';
import {Exemplaire} from "../../Exemplaire/Exemplaire";
import {ExemplaireService} from "../../Exemplaire/exemplaire.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Revue} from "../../Revue/Revue";
import {RevueService} from "../../Revue/revue.service";

@Component({
  selector: 'app-exemplaire-editor',
  templateUrl: './exemplaire-editor.component.html',
  styleUrls: ['./exemplaire-editor.component.css']
})
export class ExemplaireEditorComponent {
  exemplaire: Exemplaire = {} as Exemplaire
  updating: boolean = false
  creating: boolean = false
  revues$!: Observable<Revue[]>

  constructor(
      private exemplaireService: ExemplaireService,
      private revueService: RevueService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.exemplaire!=null){
      this.exemplaire=history.state.exemplaire
    }
    this.creating = history.state.creating
    if(this.creating) {
      this.exemplaire.statut=true;
    }
    this.updating = history.state.updating
    this.revues$=this.revueService.get()
  }

  edit() {
    if(this.updating) {
      this.exemplaireService.update(this.exemplaire)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.exemplaireService.create(this.exemplaire)
          .subscribe((location)=>this.processCreate(location))
    }
  }

  processUpdate() {
    this.router.navigate(["/exemplaires/"+this.exemplaire.id],
      {state: {exemplaire: this.exemplaire}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
