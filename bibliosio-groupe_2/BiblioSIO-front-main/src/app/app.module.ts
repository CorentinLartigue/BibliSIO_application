import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {EmprunteurListComponent} from './Emprunteur/emprunteur-list/emprunteur-list.component';
import {EmprunteurDetailsComponent} from './Emprunteur/emprunteur-details/emprunteur-details.component';
import {AppRouter} from './app-router';
import {FormsModule} from "@angular/forms";
import {EmprunteurEditorComponent} from './Emprunteur/emprunteur-editor/emprunteur-editor.component';
import { RevueListComponent } from './Revue/revue-list/revue-list.component';
import { RevueDetailsComponent } from './Revue/revue-details/revue-details.component';
import { RevueEditorComponent } from './Revue/revue-editor/revue-editor.component';
import { ExemplaireEditorComponent } from './Exemplaire/exemplaire-editor/exemplaire-editor.component';
import { ExemplaireListComponent } from './Exemplaire/exemplaire-list/exemplaire-list.component';
import { ExemplaireDetailsComponent } from './Exemplaire/exemplaire-details/exemplaire-details.component';
import { ArticleDetailsComponent } from './Article/article-details/article-details.component';
import { ArticleListComponent } from './Article/article-list/article-list.component';
import { ArticleEditorComponent } from './Article/article-editor/article-editor.component';
import { EmpruntEditorComponent } from './Emprunt/emprunt-editor/emprunt-editor.component';
import { EmpruntListComponent } from './Emprunt/emprunt-list/emprunt-list.component';
import { EmpruntDetailsComponent } from './Emprunt/emprunt-details/emprunt-details.component';

@NgModule({
  declarations: [
    AppComponent,
    EmprunteurListComponent,
    EmprunteurDetailsComponent,
    EmprunteurEditorComponent,
    RevueListComponent,
    RevueDetailsComponent,
    RevueEditorComponent,
    ExemplaireEditorComponent,
    ExemplaireListComponent,
    ExemplaireDetailsComponent,
    ArticleDetailsComponent,
    ArticleListComponent,
    ArticleEditorComponent,
    EmpruntEditorComponent,
    EmpruntListComponent,
    EmpruntDetailsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRouter,
    FormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
