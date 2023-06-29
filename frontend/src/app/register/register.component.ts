import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username = '';
  password = '';
  repeatPassword = '';
  newUser: any;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  register() {
    this.newUser = {
      username: this.username,
      password: this.password,
      repeatPassword: this.repeatPassword,
    };
    this.authService.register(this.newUser);
    this.resetForm();
  }

  resetForm(): void {
    this.username = "";
    this.password = "";
    this.repeatPassword = '';
  }

}
