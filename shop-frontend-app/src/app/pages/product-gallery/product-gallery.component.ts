import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Page} from "../../model/page";
import {ProductListParams} from "../../model/productListParams";
import {Brand} from "../../model/brand";
import {Category} from "../../model/category";
import {CartService} from "../../services/cart.service";
import {AddLineItemDto} from "../../model/add-line-item-dto";
import { ProductFilterDto } from "../../model/product-filter";
import { CheckBrand } from "../../model/check-brand";

export const PRODUCT_GALLERY_URL = 'product';

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.scss']
})
export class ProductGalleryComponent implements OnInit {

  productListParams: ProductListParams = new ProductListParams();
  page?: Page;
  brands: Brand[] = [];
  categories: Category[] = [];
  isError: boolean = false;

  constructor(private productService: ProductService,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  brandsToCheckBrands(brands: Brand[]) {
    let checkBrands: CheckBrand[] = [];
    for(var i = 0; i < brands.length; i++) {
      checkBrands.push(new CheckBrand(brands[i], false));
    }
    console.log(brands.length);
    return checkBrands;
  }

  private retrieveProducts() {
    this.productService.findAll(this.productListParams)
      .then(res => {
        this.page = res.page;
        this.brands = res.brands;
        this.categories = res.categories;
        this.isError = false;
      })
      .catch(err => {
        console.error(err);
        this.isError = true;
      })
  }

  pageNumberChange($event: number) {
    if(this.productListParams.page != $event) {
      this.productListParams.page = $event;
      this.retrieveProducts();
    }
  }

  changeCategory(newCategoryId: number) {
    if(this.productListParams.categoryId != newCategoryId) {
      this.productListParams.page = 1;
      this.productListParams.categoryId = newCategoryId;
      this.retrieveProducts();
    }
  }

  applyFilter($event: ProductFilterDto) {
    this.productListParams.page = 1;
    this.productListParams.productFilter = $event;
    this.retrieveProducts();
  }
}
