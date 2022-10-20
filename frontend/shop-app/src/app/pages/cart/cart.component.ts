import { Component, OnInit } from '@angular/core';
import {ProductCard} from "../../model/product-card";
import {ProductCardService} from "../../services/product-card/product-card.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  public products: ProductCard[] = [];

  public isLoading: boolean = false;

  public dataLoaded: boolean = false;

  constructor(private productCardService: ProductCardService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  public getProducts() {
    this.isLoading = true;
    this.productCardService.getProductCardsCart()
      .subscribe({
        next: productCard => {
          this.products.push(productCard);
          this.isLoading = false;
          this.dataLoaded = true;
        }
      });
  }

}
