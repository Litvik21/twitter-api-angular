import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationErrors: string[] = [];
  generalError: string = '';
  username = '';
  password = '';
  repeatPassword = '';
  newUser: any;

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }

  register() {
    this.newUser = {
      username: this.username,
      password: this.password,
      repeatPassword: this.repeatPassword,
    };
    this.authService.registerWithSubscription(this.newUser).subscribe(
      user => {
        console.log(user);
        this.router.navigate(['/login']);
      },
      (error: HttpErrorResponse) => {
        if (error.status === 400 && error.error && error.error.errors) {
          this.registrationErrors = error.error.errors;
        } else {
          this.generalError = 'An error occurred while registering.';
          console.error('An error occurred while registering.', error);
        }
      }
    );
  }


}
