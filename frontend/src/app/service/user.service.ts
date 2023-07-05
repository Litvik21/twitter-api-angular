import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Post } from '../model/post';
import { TokenService } from './token.service';
import { UserAccount } from '../model/userAccount';

@Injectable({providedIn: 'root'})
export class UserService {
  private usersUrl = environment.urlPath + '/users';

  constructor(
    private http: HttpClient,
    private tokenService: TokenService) {
  }

  getHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();

    if (token) {
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    }

    return new HttpHeaders();
  }

  getUser(id: any): Observable<any> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    const url = `${this.usersUrl}/${id}`;
    return this.http.get<any>(url, {headers}).pipe(
      catchError(this.handleError<any>('getUser'))
    );
  }

  getUserByUsername(username: string): Observable<UserAccount> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    const url = `${this.usersUrl}/username?username=${username}`;
    return this.http.get<UserAccount>(url, {headers}).pipe(
      catchError(this.handleError<UserAccount>('getUserByUsername'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
