import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GenericService<T> {

  protected url: string = ""
  protected httpOptions: any = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  protected className = "Generic"
  private needRefresh: boolean = true
  private lastRefresh: number = Date.now()
  private DELAY: number = 30000
  protected entities: T[] = []
  constructor(
    protected http: HttpClient
  ) {}

  private shouldRefresh(): boolean {
    return this.needRefresh||(this.lastRefresh+this.DELAY<Date.now())
  }

  get(): Observable<T[]> {
    if(this.shouldRefresh()) {
      this.needRefresh=false
      return this.http.get<T[]>(this.url)
          .pipe(map(entities=>this.entities=entities))
    }
    return of(this.entities)
  }

  getById(id: Number): Observable<T> {
    if(this.shouldRefresh()) {
      return this.http.get<T>(this.url + id)
    }
    // @ts-ignore
    return of(this.entities.find(e=>e.id==id))
  }

  create(entity: T): Observable<any> {
    this.needRefresh=true
    return this.http.request("POST", this.url, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: entity,
      observe: 'response',
      responseType: 'json'
    }).pipe(
        map(response => response.headers.get('location')),
        catchError(this.handleError<any>("create"+this.className))
    )
  }

  update(entity: T): Observable<any> {
    this.needRefresh=true
    return this.http.put(this.url, entity, this.httpOptions)
        .pipe(catchError(this.handleError<any>("update"+this.className)))


  }

  delete(entity: T): Observable<any> {
    this.needRefresh=true
    this.httpOptions.body = entity
    return this.http.delete(this.url, this.httpOptions)
      .pipe(catchError(this.handleError<any>("delete"+this.className)))
  }

  private handleError<E>(operation = "operation") {
    return (error: any): Observable<any> => {
      console.error(`${operation} failed: ${error.message}`)
      return of()
    };
  }
}
