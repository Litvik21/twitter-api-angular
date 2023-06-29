import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { UserAccount } from '../model/userAccount';
import { TokenService } from './token.service';

@Injectable({providedIn: 'root'})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private authUrl = environment.urlPath;

  constructor(
    private http: HttpClient,
    private tokenService: TokenService) {
  }

  register(user: any): Observable<UserAccount> {
    const url = `${this.authUrl}/register`;
    return this.http.post<UserAccount>(url, user, this.httpOptions).pipe(
      catchError(this.handleError<UserAccount>('register'))
    );
  }

  login(user: UserAccount): void {
    const url = `${this.authUrl}/login`;
    this.http.post<any>(url, user, this.httpOptions).subscribe(
      response => {
        const token = response.token;
        this.tokenService.saveToken(token);
      }
    )
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
