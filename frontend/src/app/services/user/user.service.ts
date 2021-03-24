import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { Login } from 'src/app/models/login';
import { User } from 'src/app/models/user';
import { environment } from 'src/environments/environment';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { PasswordChange } from 'src/app/models/password-change';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  private searchData: Subject<string> = new Subject();
  searchData$: Observable<string> = this.searchData.asObservable();

  findAll(page: number, search: string): Observable<HttpResponse<User[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '').set('search', search);
    return this.http.get<User[]>(environment.usersApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(user: User): Observable<User>{
    if (user.id){
      return this.http.put<User>(`${environment.usersApi}/${user.id}`, user).pipe(
        catchError(() => of(null))
      );
    }
    return this.http.post<User>(environment.usersApi, user).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.usersApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  login(login: Login): Observable<User>{
    return this.http.post<User>(`${environment.authApi}/login`, login).pipe(
      catchError(() => of(null))
    );
  }

  changePassword(passwordChange: PasswordChange): Observable<boolean>{
    return this.http.put<null>(`${environment.usersApi}/password-change`, passwordChange).pipe(
      map(() => true),
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
