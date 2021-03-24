import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { Account } from 'src/app/models/account';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  private searchData: Subject<string> = new Subject();
  searchData$: Observable<string> = this.searchData.asObservable();

  private report: Subject<number> = new Subject();
  report$: Observable<number> = this.report.asObservable();

  findAll(page: number, search: string): Observable<HttpResponse<Account[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '').set('search', search);
    return this.http.get<Account[]>(environment.accountsApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(account: Account): Observable<Account>{
    if (account.id){
      return this.http.put<Account>(`${environment.accountsApi}/${account.id}`, account).pipe(
        catchError(() => of(null))
      );
    }
    return this.http.post<Account>(environment.accountsApi, account).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.accountsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  myAccount(): Observable<Account>{
    return this.http.get<Account>(`${environment.accountsApi}/my`).pipe(
      catchError(() => of(null))
    );
  }

  getReport(index: number): Observable<Account[]>{
    return this.http.get<Account[]>(`${environment.accountsApi}/report-${index}`).pipe(
      catchError(() => of([]))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

  announceSearchData(search: string): void{
    this.searchData.next(search);
  }

  announceReport(index: number): void{
    this.report.next(index);
  }
}
