import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CategoryUrls {
  public all: string = 'api/v1/products/category/all';
}
