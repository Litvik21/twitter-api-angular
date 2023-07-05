import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginErrors: string[] = [];
  generalError: string = '';
  username = '';
  password = '';
  user: any;

  constructor(private authService: AuthService,
              private router: Router,
              private tokenService: TokenService) { }

  ngOnInit(): void {
  }

  login() {
    this.user = {
      username: this.username,
      password: this.password,
    };
    this.authService.login(this.user).subscribe(
      response => {
        const token = response.token;
        this.authService.isLoggedInSubject.next(true);
        this.tokenService.setCurrentUser({ id: response.id, username: response.username });
        this.tokenService.saveToken(token);
        this.router.navigate(['/main']);
      },
      (error: HttpErrorResponse) => {
        if (error.status === 500 && error.error && error.error.message) {
          this.loginErrors = [error.error.message];
        } else {
          this.generalError = 'An error occurred while logging.';
          console.error('An error occurred while logging.', error);
        }
      }
    );
  }

}
