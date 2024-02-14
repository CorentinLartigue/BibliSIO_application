import {Component} from '@angular/core';
import {Revue} from "../Revue";
import {RevueService} from "../revue.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-revue-list',
  templateUrl: './revue-list.component.html',
  styleUrls: ['./revue-list.component.css']
})
export class RevueListComponent {
  revues: Revue[]

  constructor(
      private revueService: RevueService,
      private router: Router
  ) {
    this.revues = []
    revueService.get()
        .subscribe((revues)=>this.revues=revues)
  }

  openRevueDetails(revue: Revue) {
    this.router.navigate(['/revues/'+revue.id],
        {state: {revue: revue, solo: true}})
  }

  openCreateRevue() {
    this.router.navigate(['/revues/edit'],
        {state: {creating: true}})
  }
}
