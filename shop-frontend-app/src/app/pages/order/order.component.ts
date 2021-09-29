import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.findOrders();
  }

  private findOrders(): void {
    this.orderService.findOrders()
      .subscribe(orders => {
          this.orders = orders;
        },
        error => {
          console.log(error);
        })
  }

  cancelOrder(orderId: number) {
    this.orderService.cancelOrder(orderId)
      .subscribe(() => {
          this.findOrders();
        },
        error => {
          console.log(error);
        })
  }
}
