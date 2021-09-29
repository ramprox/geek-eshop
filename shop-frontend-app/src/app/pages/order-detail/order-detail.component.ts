import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../services/order.service";
import {OrderDetails} from "../../model/order-details";
import {ActivatedRoute} from "@angular/router";

export const ORDER_DETAILS_URL = 'orderdetails/:id';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {

  orderDetails?: OrderDetails;
  isError: boolean = false;

  constructor(private orderService: OrderService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
          this.orderService.findOrderById(param.id)
            .then(res => {
              this.isError = false;
              this.orderDetails = res;
            })
            .catch(err => {
              console.error(err);
              this.isError = true;
            });
        })
  }

}
