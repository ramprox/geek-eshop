import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Product} from "../model/product";
import {Page} from "../model/page";
import {ProductListParams} from "../model/productListParams";
import {ProductsPage} from "../model/productsPage";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public findAll(productListParams: ProductListParams) {
    let queryParams = new HttpParams();
    if(productListParams.categoryId >= 0) {
      queryParams = queryParams.set('categoryId', productListParams.categoryId);
    }
    if(productListParams.page > 1) {
      queryParams = queryParams.set('page', productListParams.page);
    }
    if(productListParams.productName != '') {
      queryParams = queryParams.set('productName', productListParams.productName);
    }
    if(productListParams.minCost != null && productListParams.minCost > 0) {
      queryParams = queryParams.set('minCost', productListParams.minCost);
    }
    if(productListParams.maxCost != null && productListParams.maxCost > 0) {
      queryParams = queryParams.set('maxCost', productListParams.maxCost);
    }
    return this.http.get<ProductsPage>('/api/v1/product/all', {params : queryParams}).toPromise();
  }

  public findById(id: number) {
    return this.http.get<Product>(`/api/v1/product/${id}`).toPromise();
  }
}
