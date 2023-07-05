import { Injectable } from '@angular/core';
import { CurrentUser } from '../model/currentUser';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'token';
  private readonly USER_KEY = 'user';

  getCurrentUser(): CurrentUser {
    const userData = localStorage.getItem(this.USER_KEY);
    return JSON.parse(userData!);
  }

  setCurrentUser(user: CurrentUser): void {
    console.log(user);
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }
}
