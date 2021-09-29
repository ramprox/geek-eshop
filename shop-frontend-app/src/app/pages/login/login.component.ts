import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Credentials} from "../../model/credentials";
import {Router} from "@angular/router";
import {PRODUCT_GALLERY_URL} from "../product-gallery/product-gallery.component";

export const LOGIN_URL = 'login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  credentials: Credentials = new Credentials("", "")

  isError: boolean = false;

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    this.auth.authenticate(this.credentials)
      .subscribe(authResult => {
        this.isError = false;
        if (authResult.redirectUrl) {
          this.router.navigateByUrl(authResult.redirectUrl);
        } else {
          this.router.navigateByUrl('/' + PRODUCT_GALLERY_URL);
        }
      }, error => {
        this.isError = true;
        console.log(`Authentication error ${error}`);
      })
  }
}
