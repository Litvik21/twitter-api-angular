import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Post } from '../model/post';
import { TokenService } from './token.service';

@Injectable({providedIn: 'root'})
export class PostService {
  private postsUrl = environment.urlPath + '/posts';

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

  addPost(formData: FormData): Observable<any> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.http.post<any>(`${this.postsUrl}/add`, formData, { headers }).pipe(
      catchError(this.handleError<any>('addPost'))
    );
  }

  removePost(id: number): Observable<any> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    const url = `${this.postsUrl}/remove/${id}`;
    return this.http.delete(url, {headers}).pipe(
      catchError(this.handleError<any>(`removePost id=${id}`))
    );
  }

  getPosts(): Observable<Post[]> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    return this.http.get<Post[]>(this.postsUrl, {headers}).pipe(
      catchError(this.handleError<Post[]>('getPosts', []))
    );
  }

  getPostOfCurrentUser(): Observable<Post[]> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    return this.http.get<Post[]>(`${this.postsUrl}/my`, {headers}).pipe(
      catchError(this.handleError<Post[]>('getPosts', []))
    );
  }

  getPostsOfUser(username: string): Observable<Post[]> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    const url = `${this.postsUrl}/username?username=${username}`;
    return this.http.get<Post[]>(url, {headers}).pipe(
        catchError(this.handleError<Post[]>('getPosts', []))
      );
  }

  addOrRemoveLike(id: number): Observable<Post> {
    const headers = this.getHeaders();
    headers.append('Content-Type', 'application/json');
    const url = `${this.postsUrl}/like/${id}`;
    return this.http.get<Post>(url, {headers}).pipe(
      catchError(this.handleError<Post>(`addOrRemoveLike id=${id}`))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
