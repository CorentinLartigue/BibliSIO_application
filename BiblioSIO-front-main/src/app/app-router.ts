import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {EmprunteurListComponent} from "./Emprunteur/emprunteur-list/emprunteur-list.component";
import {EmprunteurDetailsComponent} from "./Emprunteur/emprunteur-details/emprunteur-details.component";
import {EmprunteurEditorComponent} from "./Emprunteur/emprunteur-editor/emprunteur-editor.component";
import {RevueListComponent} from "./Revue/revue-list/revue-list.component";
import {RevueEditorComponent} from "./Revue/revue-editor/revue-editor.component";
import {RevueDetailsComponent} from "./Revue/revue-details/revue-details.component";
import {ExemplaireListComponent} from "./Exemplaire/exemplaire-list/exemplaire-list.component";
import {ExemplaireEditorComponent} from "./Exemplaire/exemplaire-editor/exemplaire-editor.component";
import {ExemplaireDetailsComponent} from "./Exemplaire/exemplaire-details/exemplaire-details.component";
import {ArticleListComponent} from "./Article/article-list/article-list.component";
import {ArticleEditorComponent} from "./Article/article-editor/article-editor.component";
import {ArticleDetailsComponent} from "./Article/article-details/article-details.component";
import {EmpruntEditorComponent} from "./Emprunt/emprunt-editor/emprunt-editor.component";
import {EmpruntDetailsComponent} from "./Emprunt/emprunt-details/emprunt-details.component";
import {EmpruntListComponent} from "./Emprunt/emprunt-list/emprunt-list.component";

const routes: Routes = [
  { path: 'emprunteurs', component: EmprunteurListComponent },
  { path: 'emprunteurs/edit', component: EmprunteurEditorComponent },
  { path: 'emprunteurs/:id', component: EmprunteurDetailsComponent },
  { path: 'revues', component: RevueListComponent },
  { path: 'revues/edit', component: RevueEditorComponent },
  { path: 'revues/:id', component: RevueDetailsComponent },
  { path: 'exemplaires', component: ExemplaireListComponent },
  { path: 'exemplaires/edit', component: ExemplaireEditorComponent },
  { path: 'exemplaires/:id', component: ExemplaireDetailsComponent },
  { path: 'articles', component: ArticleListComponent },
  { path: 'articles/edit', component: ArticleEditorComponent },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'emprunts', component: EmpruntListComponent },
  { path: 'emprunts/edit', component: EmpruntEditorComponent },
  { path: 'emprunts/:id', component: EmpruntDetailsComponent },
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes, {bindToComponentInputs: true})],
  exports: [RouterModule]
})
export class AppRouter {}
