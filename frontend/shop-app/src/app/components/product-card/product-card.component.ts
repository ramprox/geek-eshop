import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductCard} from "../../model/product-card";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {

  @Input() product: ProductCard = new ProductCard();
  @Input() isAddedToFavorites: boolean = false;
  @Output() productDeleted: EventEmitter<ProductCard> = new EventEmitter<ProductCard>();

  constructor() { }

  ngOnInit(): void {
  }

}
