import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProductGalleryComponent } from './pages/product-gallery/product-gallery.component';
import { ProductInfoComponent } from './pages/product-info/product-info.component';
import { CartComponent } from './pages/cart/cart.component';
import { ProductGalleryCardComponent } from './components/product-gallery-card/product-gallery-card.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { ProductFilterComponent } from './components/product-filter/product-filter.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    ProductGalleryComponent,
    ProductInfoComponent,
    CartComponent,
    ProductGalleryCardComponent,
    PaginationComponent,
    ProductFilterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
