import {Component, Input} from '@angular/core';
import {EmprunteurService} from "../emprunteur.service";
import {Emprunteur} from "../Emprunteur";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-emprunteur-details',
  templateUrl: './emprunteur-details.component.html',
  styleUrls: ['./emprunteur-details.component.css']
})
export class EmprunteurDetailsComponent {

  @Input() emprunteur!: Emprunteur
  @Input() solo: boolean = true

  constructor(
      private emprunteurService: EmprunteurService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.emprunteur==null) {
      this.emprunteur=history.state.emprunteur
      console.log(history)
      console.log(history.state.emprunteur==null)
      if (history.state.emprunteur==null) {
        this.emprunteurService.getById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
            .subscribe(emprunteur=>this.emprunteur=emprunteur)
      }
    }
    this.solo=history.state.solo
  }

  delete(){
    this.emprunteurService.delete(this.emprunteur)
        .subscribe(()=>this.router.navigate(['/emprunteurs']))
  }

  update() {
    this.router.navigate(["/emprunteurs/edit"],
        {state: {emprunteur: this.emprunteur,
                        updating: true}})
  }

}
