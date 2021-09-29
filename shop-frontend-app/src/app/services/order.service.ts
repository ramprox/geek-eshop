import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {OrderDetails} from "../model/order-details";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  public findOrders() {
    return this.http.get<Order[]>('/api/v1/order');
  }

  public createOrder() {
    return this.http.post<Order[]>('/api/v1/order/create', {});
  }

  public findOrderById(orderId: number) {
    return this.http.get<OrderDetails>(`/api/v1/order/${orderId}`).toPromise();
  }

  public cancelOrder(orderId: number) {
    return this.http.delete('/api/v1/order', ({body: orderId}));
  }
}
