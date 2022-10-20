import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  main = '/main';
  signup = '/sign_up';
  signin = '/sign_in';
  favorites = '/favorites';
  users = '/users';

  currentUrl: string = this.main;

  leftMenuIsActive: boolean = false;

  constructor(private router: Router) {
    router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      ).subscribe({
      next: value => {
        this.currentUrl = (value as NavigationEnd).url;
      }
    })
  }

  ngOnInit(): void { }

  logout() {
    // this.authService.logout();
    this.router.navigateByUrl(this.main);
  }


  clickLeftMenu() {
    this.leftMenuIsActive = !this.leftMenuIsActive;
  }

  getUsername() {
    return "user"
    // return this.authService.getUsername();
  }
}
