import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';
  user: any;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  login() {
    this.user = {
      username: this.username,
      password: this.password,
    };
    this.authService.register(this.user);
    this.resetForm();
  }

  resetForm(): void {
    this.username = "";
    this.password = "";
  }
}
