import { Component } from '@angular/core';
import {Article} from "../../Article/Article";
import {ArticleService} from "../../Article/article.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent {
  articles: Article[]

  constructor(
      private articleService: ArticleService,
      private router: Router
  ) {
    this.articles = []
    articleService.get()
        .subscribe((articles)=>this.articles=articles)
  }

  openArticleDetails(article: Article) {
    this.router.navigate(['/articles/'+article.id],
        {state: {article: article, solo: true}})
  }

  openCreateArticle() {
    this.router.navigate(['/articles/edit'],
        {state: {creating: true}})
  }
}
