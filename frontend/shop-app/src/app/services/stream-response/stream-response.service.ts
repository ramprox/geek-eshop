import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StreamResponseService {

  constructor(private ngZone: NgZone) { }

  public getStreamResponse<T>(url: string): Observable<T> {
    return new Observable<T>((
      observer => {
        let source = new EventSource(url);
        source.onmessage = event => {
          let object = JSON.parse(event.data);
          this.ngZone.run(() => {
            observer.next(object);
          })
        };
        source.onerror = error => {
          this.ngZone.run(() => {
            observer.error(error)
          })
        };
        return () => source.close();
      }
    ));
  }
}
