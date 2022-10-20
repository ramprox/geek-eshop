import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CartUrls {
  public all: string = '/api/v1/orders/cart/all';
}
