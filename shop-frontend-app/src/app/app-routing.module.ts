import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PRODUCT_GALLERY_URL, ProductGalleryComponent} from "./pages/product-gallery/product-gallery.component";
import {ProductInfoComponent} from "./pages/product-info/product-info.component";

const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: PRODUCT_GALLERY_URL},
  {path: PRODUCT_GALLERY_URL, component: ProductGalleryComponent},
  {path: "product/:id", component: ProductInfoComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
