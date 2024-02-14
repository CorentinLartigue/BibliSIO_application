import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Article} from "./Article";

@Injectable({
  providedIn: 'root'
})
export class ArticleService extends GenericService<Article> {
  protected override className = "Article"
  protected override url = "http://localhost:8080/articles"
}
