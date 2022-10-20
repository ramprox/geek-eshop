import { Injectable } from '@angular/core';
import {StreamResponseService} from "../stream-response/stream-response.service";
import {Observable} from "rxjs";
import {Brand} from "../../dto/brand";
import {BrandUrls} from "../../config/brand-urls";

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  constructor(private streamResponseService: StreamResponseService,
              private brandUrls: BrandUrls) { }

  public getBrands(): Observable<Brand> {
    return this.streamResponseService.getStreamResponse(this.brandUrls.all);
  }

}
