import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class PictureUrls {
  public all: string = '/api/v1/pictures/picture'
}
