import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {FrontUrls} from "./config/app-config";
import {CartComponent} from "./pages/cart/cart.component";

const routes: Routes = [
  { path: "", pathMatch: "full", redirectTo: FrontUrls.MAIN },
  { path: FrontUrls.MAIN, component: MainComponent },
  { path: FrontUrls.CART, component: CartComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
