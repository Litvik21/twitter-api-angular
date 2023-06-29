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
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      });
    }

    return new HttpHeaders();
  }

  addPost(post: Post): Observable<Post> {
    const headers = this.getHeaders();
    return this.http.post<Post>(this.postsUrl, post, {headers}).pipe(
      catchError(this.handleError<Post>('addPost'))
    );
  }

  removePost(id: number): Observable<any> {
    const headers = this.getHeaders();
    const url = `${this.postsUrl}/remove/${id}`;
    return this.http.delete(url, {headers}).pipe(
      catchError(this.handleError<any>(`removePost id=${id}`))
    );
  }

  getPostsOfUser(id: number): Observable<Post[]> {
    const headers = this.getHeaders();
    const url = `${this.postsUrl}/all/${id}`;
    return this.http.get<Post[]>(url, {headers}).pipe(
        catchError(this.handleError<Post[]>('getPosts', []))
      );
  }

  addOrRemoveLike(id: number): Observable<Post> {
    const headers = this.getHeaders();
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
