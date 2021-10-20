import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";
import {OrderStatusService} from "../../services/order-status.service";

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.findOrders();
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        for(var i = 0; i < this.orders.length; i++) {
          if(this.orders[i].id == msg.id) {
            this.orders[i].status = msg.state;
          }
        }
      });
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
