import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { BaseReport } from 'src/app/models/base-report';
import { Bill } from 'src/app/models/bill';
import { BillRequest } from 'src/app/models/bill-request';
import { BillResponse } from 'src/app/models/bill-response';
import { RuleResponse } from 'src/app/models/rule-response';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  private searchData: Subject<string> = new Subject();
  searchData$: Observable<string> = this.searchData.asObservable();

  findAll(type: string, page: number, search: string): Observable<HttpResponse<Bill[]>>{
    const params = new HttpParams().set('rsd', (type === 'rsd') + '')
    .set('page', page + '').set('size', PAGE_SIZE + '').set('search', search);
    return this.http.get<Bill[]>(environment.billsApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  create(response: BillRequest): Observable<BillResponse>{
    return this.http.post<Bill>(environment.billsApi, response).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<RuleResponse>{
    return this.http.delete<null>(`${environment.billsApi}/${id}`).pipe(
      catchError(() => of(null))
    );
  }

  terms(request: BillRequest): Observable<BillResponse>{
    return this.http.post<BillResponse>(`${environment.billsApi}/terms`, request).pipe(
      catchError(() => of(null))
    );
  }

  transaction(id: number, amount: number): Observable<RuleResponse>{
    return this.http.put(`${environment.billsApi}/${id}/increase/${amount}`, null).pipe(
      catchError(() => of(null))
    );
  }

  renew(id: number, amount: number): Observable<RuleResponse>{
    return this.http.put(`${environment.billsApi}/${id}/renew/${amount}`, null).pipe(
      catchError(() => of(null))
    );
  }

  baseReport(): Observable<BaseReport>{
    return this.http.get<BaseReport>(`${environment.billsApi}/base-report`).pipe(
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

  announceSearchData(search: string): void{
    this.searchData.next(search);
  }

}
