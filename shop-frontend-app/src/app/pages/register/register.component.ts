import { Component, OnInit } from '@angular/core';
import { RegisterData } from "../../model/register-data";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import { LOGIN_URL } from "../login/login.component";
import {RegisterErrors} from "../../model/register-errors";

export const REGISTER_URL = 'register';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerData: RegisterData = new RegisterData('', '', '', '', '', '');

  repeatPassword: string = '';

  isError: boolean = false;

  errors?: RegisterErrors;

  date: Date = new Date();

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  register() {
    if(this.repeatPassword != this.registerData.password) {
      this.isError = true;
      return;
    } else {
      this.isError = false;
    }
    this.auth.register(this.registerData)
      .subscribe(registerResult => {
        this.isError = false;
        if (registerResult.redirectUrl) {
          this.router.navigateByUrl(registerResult.redirectUrl);
        } else {
          this.router.navigateByUrl('/' + LOGIN_URL);
        }
      }, error => {
        this.isError = true;
        this.errors = error.error;
        console.log(`Authentication error ${error}`);
      })
  }
}
