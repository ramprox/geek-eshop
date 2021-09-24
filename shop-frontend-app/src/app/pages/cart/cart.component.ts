import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {AddLineItemDto} from "../../model/add-line-item-dto";
import {LineItem} from "../../model/line-item";

export const CART_URL = 'cart';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  content?: AllCartDto;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }

  updateLineItem(inputId: string, lineItem: LineItem) {
    let input = document.getElementById(inputId) as HTMLInputElement;
    let newQty = +input.value;
    if(newQty > 0) {
      let lineItemQuery = new LineItem(lineItem.productId, lineItem.productDto, newQty, lineItem.color, lineItem.material, lineItem.itemTotal);
      this.cartService.updateLineItem(lineItemQuery).subscribe(
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
    this.cartService.clearCart().subscribe(
      res => {
        this.content = res;
      }
    )
  }
}
