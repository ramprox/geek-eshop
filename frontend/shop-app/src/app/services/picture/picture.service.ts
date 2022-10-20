import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PictureUrls} from "../../config/picture-urls";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PictureService {

  constructor(private http: HttpClient,
              private pictureUrls: PictureUrls) { }

  public getImageLinksByProductId(productId: string): Observable<string[]> {
    return this.http.get<string[]>(this.pictureUrls.all,
      {params: {productId: productId}})
      .pipe(map(value => {
        let links: string[] = [];
        for(let i = 0; i < value.length; i++) {
          links[i] = this.pictureUrls.all + '/' + value[i];
        }
        return links;
      }))
  }

}
