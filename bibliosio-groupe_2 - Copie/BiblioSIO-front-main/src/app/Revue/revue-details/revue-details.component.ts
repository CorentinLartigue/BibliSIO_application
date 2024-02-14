import {Component, Input} from '@angular/core';
import {Revue} from "../Revue";
import {RevueService} from "../revue.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-revue-details',
  templateUrl: './revue-details.component.html',
  styleUrls: ['./revue-details.component.css']
})
export class RevueDetailsComponent {
  @Input() revue!: Revue
  @Input() solo: boolean = true

  constructor(
      private revueService: RevueService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.revue==null) {
      this.revue=history.state.revue
      if (history.state.revue==null) {
        this.revueService.getById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
            .subscribe(revue=>this.revue=revue)
      }
    }
    this.solo=history.state.solo
  }

  delete(){
    this.revueService.delete(this.revue)
        .subscribe(()=>this.router.navigate(['/revues']))
  }

  update() {
    this.router.navigate(["/revues/edit"],
        {state: {revue: this.revue,
            updating: true}})
  }
}
