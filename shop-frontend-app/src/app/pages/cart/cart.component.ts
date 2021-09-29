import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {AddLineItemDto} from "../../model/add-line-item-dto";
import {LineItem} from "../../model/line-item";
import {OrderService} from "../../services/order.service";

export const CART_URL = 'cart';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  content?: AllCartDto;

  constructor(private cartService: CartService,
              private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }

  updateLineItem(lineItem: LineItem) {
    if(lineItem.qty > 0) {
      this.cartService.updateLineItem(lineItem).subscribe(
        res => {
          this.content = res;
        }
      );
    }
  }

  deleteLineItem(lineItem: LineItem) {
    this.cartService.deleteLineItem(lineItem).subscribe(
      res => {
        this.content = res;
      }
    );
  }

  clearCart() {
    if(this.content != null && this.content.lineItems.length > 0) {
      this.cartService.clearCart().subscribe(
        res => {
          this.content = res;
        }
      )
    }
  }

  createOrder() {
    if(this.content != null && this.content.lineItems.length > 0) {
      this.orderService.createOrder().subscribe(
        res => {
          this.content = undefined;
        }
      );
    }
  }
}
