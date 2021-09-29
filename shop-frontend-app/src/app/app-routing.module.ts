import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PRODUCT_GALLERY_URL, ProductGalleryComponent} from "./pages/product-gallery/product-gallery.component";
import {PRODUCT_INFO_URL, ProductInfoComponent} from "./pages/product-info/product-info.component";
import {CART_URL, CartComponent} from "./pages/cart/cart.component";
import {LOGIN_URL, LoginComponent} from "./pages/login/login.component";
import {REGISTER_URL, RegisterComponent} from "./pages/register/register.component";
import {OrderComponent, ORDERS_URL} from "./pages/order/order.component";
import {OrderDetailComponent, ORDER_DETAILS_URL} from "./pages/order-detail/order-detail.component";
import {AuthGuard} from "./helpers/auth-guard";


const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: PRODUCT_GALLERY_URL},
  {path: PRODUCT_GALLERY_URL, component: ProductGalleryComponent},
  {path: PRODUCT_INFO_URL, component: ProductInfoComponent},
  {path: CART_URL, component: CartComponent},
  {path: LOGIN_URL, component: LoginComponent},
  {path: REGISTER_URL, component: RegisterComponent},
  {path: ORDERS_URL, component: OrderComponent, canActivate: [AuthGuard]},
  {path: ORDER_DETAILS_URL, component: OrderDetailComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
