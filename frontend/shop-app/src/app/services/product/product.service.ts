import {Injectable} from '@angular/core';
import {StreamResponseService} from "../stream-response/stream-response.service";
import {map, Observable} from "rxjs";
import {ProductUrls} from "../../config/product-urls";
import {ListProduct} from "../../dto/list-product";
import {ProductCard} from "../../model/product-card";
import {ProductFilter} from "./product-filter";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private streamResponseService: StreamResponseService,
              private http: HttpClient,
              private productUrls: ProductUrls) { }

  public getProducts(name: string, productFilter: ProductFilter): Observable<ProductCard> {
    let url = this.productUrls.all;
    let params = new HttpParams();
    if(name !== '') {
      params = params.set('productName', name);
    }
    let minPrice = productFilter.minPrice;
    let maxPrice = productFilter.maxPrice;
    if(minPrice != -1 || maxPrice != -1) {
      params = params.set('price.min', minPrice);
      params = params.set('price.max', maxPrice);
    }
    let categoryId = productFilter.categoryId;
    if(categoryId !== '') {
      params = params.set('categoryId', categoryId);
    }
    let page = productFilter.page;
    if(page > 1) {
      params = params.set('page', page);
    }
    url  = `${url}?${params.toString()}`;
    return this.streamResponseService
      .getStreamResponse<ListProduct>(url)
      .pipe(map(listProduct => {
        let productCard: ProductCard = new ProductCard();
        productCard.listProduct = listProduct;
        productCard.link = this.productUrls.single.replace('{id}', listProduct.id);
        return productCard;
      }));
  }

  public getProduct(productId: number): Observable<ProductCard> {
    let url = this.productUrls.single.replace('{id}', String(productId));
    return this.http.get<ListProduct>(url)
      .pipe(map(listProduct => {
        let productCard: ProductCard = new ProductCard();
        productCard.listProduct = listProduct;
        productCard.link = url;
        return productCard;
      }));
  }

}
