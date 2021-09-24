import { Component, Input, OnInit } from '@angular/core';
import {Product} from "../../model/product";
import {CartService} from "../../services/cart.service";
import {AddLineItemDto} from "../../model/add-line-item-dto";

@Component({
  selector: 'app-product-gallery-card',
  templateUrl: './product-gallery-card.component.html',
  styleUrls: ['./product-gallery-card.component.scss']
})
export class ProductGalleryCardComponent implements OnInit {

  @Input() products?: Product[];

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
  }

  addToCart(id: number) {
      this.cartService.addToCart(new AddLineItemDto(id, 1, "", ""))
        .subscribe();
    }
}
