import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PRODUCT_GALLERY_URL, ProductGalleryComponent} from "./pages/product-gallery/product-gallery.component";
import {PRODUCT_INFO_URL, ProductInfoComponent} from "./pages/product-info/product-info.component";
import {CART_URL, CartComponent} from "./pages/cart/cart.component";


const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: PRODUCT_GALLERY_URL},
  {path: PRODUCT_GALLERY_URL, component: ProductGalleryComponent},
  {path: PRODUCT_INFO_URL, component: ProductInfoComponent},
  {path: CART_URL, component: CartComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
