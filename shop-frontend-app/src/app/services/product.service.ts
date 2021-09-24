import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Product} from "../model/product";
import {Page} from "../model/page";
import {ProductListParams} from "../model/productListParams";
import {ProductsPage} from "../model/productsPage";
import {ProductFilterDto} from "../model/product-filter";

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
    let productFilter = productListParams.productFilter;
    if(productFilter.namePattern != null && productFilter.namePattern != '') {
      queryParams = queryParams.set('productName', productFilter.namePattern);
    }
    if(productFilter.minCost != null && productFilter.minCost > 0) {
      queryParams = queryParams.set('minCost', productFilter.minCost);
    }
    if(productFilter.maxCost != null && productFilter.maxCost > 0) {
      queryParams = queryParams.set('maxCost', productFilter.maxCost);
    }
    if(productFilter.brands.size > 0) {
      for(let entry of productFilter.brands.entries()) {
        if(entry[1].isChecked) {
         queryParams = queryParams.append('brandIds', entry[0]);
        }
      }
    }
    return this.http.get<ProductsPage>('/api/v1/product/all', {params : queryParams}).toPromise();
  }

  public findById(id: number) {
    return this.http.get<Product>(`/api/v1/product/${id}`).toPromise();
  }
}
