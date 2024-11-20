import {Component, Input} from '@angular/core';
import {Article} from "../../Article/Article";
import {ArticleService} from "../../Article/article.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css']
})
export class ArticleDetailsComponent {
  @Input() article!: Article
  @Input() solo: boolean = true

  constructor(
      private articleService: ArticleService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.article==null) {
      this.article=history.state.article
      if (history.state.article==null) {
        this.articleService.getById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
            .subscribe(article=>this.article=article)
      }
    }
    this.solo=history.state.solo
  }

  delete(){
    this.articleService.delete(this.article)
        .subscribe(()=>this.router.navigate(['/articles']))
  }

  update() {
    this.router.navigate(["/articles/edit"],
        {state: {article: this.article,
            updating: true}})
  }
}
