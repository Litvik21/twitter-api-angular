import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { environment } from '../../environments/environment';
import { TokenService } from './token.service';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable({providedIn: 'root'})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private authUrl = environment.urlPath;
  public isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private userService: UserService,
    private router: Router) {
  }

  registerWithSubscription(user: any): Observable<any> {
    const url = `${this.authUrl}/register`;
    return this.http.post<any>(url, user, this.httpOptions);
  }

  login(user: any): Observable<any> {
    const url = `${this.authUrl}/login`;
    return this.http.post<any>(url, user, this.httpOptions);
  }

  // login(user: any): void {
  //   const url = `${this.authUrl}/login`;
  //   this.http.post<any>(url, user, this.httpOptions).subscribe(
  //     response => {
  //       const token = response.token;
  //       this.tokenService.setCurrentUser({ id: response.id, username: response.username });
  //       this.tokenService.saveToken(token);
  //       this.isLoggedInSubject.next(true);
  //       this.router.navigate(['/main']);
  //     }
  //   )
  //
  // }

  logout(): void {
    this.tokenService.removeToken();
    this.isLoggedInSubject.next(false);
    this.router.navigate(['/login']);
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
