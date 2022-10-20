import {Injectable, NgZone, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Category} from "../../dto/category";
import {StreamResponseService} from "../stream-response/stream-response.service";
import {CategoryUrls} from "../../config/category-urls";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private streamResponseService: StreamResponseService,
              private categoryUrls: CategoryUrls) { }

  public getCategories(parentCategoryId: number | null): Observable<Category> {
    let url = this.categoryUrls.all;
    if(parentCategoryId != null) {
      let params = new HttpParams().set('categoryId', parentCategoryId);
      url = `${url}?${params.toString()}`;
    }
    return this.streamResponseService
      .getStreamResponse(url);
  }
}
