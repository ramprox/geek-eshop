import { Injectable } from '@angular/core';
import {StreamResponseService} from "../stream-response/stream-response.service";
import {CartUrls} from "../../config/cart-urls";
import {map, Observable} from "rxjs";
import {ProductCard} from "../../model/product-card";
import {ListProduct} from "../../dto/list-product";
import {ProductUrls} from "../../config/product-urls";
import {ProductCart} from "../../dto/product-cart";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private streamResponseService: StreamResponseService,
              private cartUrls: CartUrls) { }

  public getProductsFromCart(): Observable<ProductCart> {
    return this.streamResponseService
      .getStreamResponse<ProductCart>(this.cartUrls.all);
  }

}
