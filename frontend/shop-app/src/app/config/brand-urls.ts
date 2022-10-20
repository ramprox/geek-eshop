import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class BrandUrls {
  public all: string = 'api/v1/products/brand/all';
}
