import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './main-app/app.component';
import { MainComponent } from './pages/main/main.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatDividerModule} from "@angular/material/divider";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatSliderModule} from "@angular/material/slider";
import {NgxSliderModule} from "@angular-slider/ngx-slider";
import {MatInputModule} from "@angular/material/input";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { ProductCardComponent } from './components/product-card/product-card.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import { FiltersComponent } from './components/filters/filters.component';
import { CategoriesComponent } from './components/categories/categories.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { CartComponent } from './pages/cart/cart.component';

@NgModule({
    declarations: [
        AppComponent,
        MainComponent,
        NavBarComponent,
        ProductCardComponent,
        FiltersComponent,
        CategoriesComponent,
        CartComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatDividerModule,
    MatButtonModule,
    MatIconModule,
    MatSliderModule,
    NgxSliderModule,
    MatInputModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatSidenavModule,
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
