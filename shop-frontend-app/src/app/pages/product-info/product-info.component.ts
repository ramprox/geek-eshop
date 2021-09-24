import { Component, OnInit } from '@angular/core';
import {Product} from "../../model/product";
import {ProductService} from "../../services/product.service";
import {ActivatedRoute} from "@angular/router";

export const PRODUCT_INFO_URL = 'product/:id';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent implements OnInit {

  product: Product  | null = null;
  isError: boolean = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.route.params.subscribe(param => {
      this.productService.findById(param.id)
        .then(res => {
          this.isError = false;
          this.product = res;
        })
        .catch(err => {
          console.error(err);
          this.isError = true;
        });
    })
  }
}
