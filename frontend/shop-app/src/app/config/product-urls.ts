import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ProductUrls {
  public all: string = 'api/v1/products/product/all';
  public single: string = 'api/v1/products/product/{id}';
}
