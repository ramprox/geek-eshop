import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {ProductCard} from "../../model/product-card";
import {ProductCardService} from "../../services/product-card/product-card.service";
import {FiltersComponent} from "../../components/filters/filters.component";
import {ProductFilter} from "../../services/product/product-filter";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, AfterViewInit {

  public searchName: string = '';

  public products: ProductCard[] = [];

  @ViewChild('filters') filtersComponent!: FiltersComponent;

  constructor(private productCardService: ProductCardService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  ngAfterViewInit() {
    // this.products = [];
    // this.getProducts();
  }

  public getProducts() {
    let productFilter: ProductFilter;
    if(this.filtersComponent) {
      productFilter = this.filtersComponent.productFilter;
    } else {
      productFilter = new ProductFilter();
    }
    this.productCardService.getProductCards(this.searchName, productFilter)
      .subscribe({
        next: productCard => {
          this.products.push(productCard);
        }
      });
  }

  public search() {
    this.products = [];
    this.filtersComponent.reset();
    this.getProducts();
  }

  forwardPage() {
    this.filtersComponent.productFilter.page++;
    this.getProducts();
  }

}
