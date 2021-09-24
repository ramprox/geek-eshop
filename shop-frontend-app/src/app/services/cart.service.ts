import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AddLineItemDto} from "../model/add-line-item-dto";
import {Observable} from "rxjs";
import {AllCartDto} from "../model/all-cart-dto";
import {LineItem} from "../model/line-item";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  public findAll() : Observable<AllCartDto> {
    return this.http.get<AllCartDto>('/api/v1/cart/all');
  }

  public addToCart(dto: AddLineItemDto) {
    return this.http.post('/api/v1/cart', dto);
  }

  public clearCart() : Observable<AllCartDto> {
    return this.http.delete<AllCartDto>('/api/v1/cart');
  }

  public updateLineItem(lineItem: LineItem) : Observable<AllCartDto> {
    return this.http.put<AllCartDto>('/api/v1/cart', lineItem);
  }

  public deleteLineItem(lineItem: LineItem) : Observable<AllCartDto> {
    return this.http.delete<AllCartDto>('/api/v1/cart', ({body: lineItem}));
  }
}
