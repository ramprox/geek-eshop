import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Page} from "../../model/page";
import {ProductListParams} from "../../model/productListParams";
import {Brand} from "../../model/brand";
import {Category} from "../../model/category";

export const PRODUCT_GALLERY_URL = 'product';

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.scss']
})
export class ProductGalleryComponent implements OnInit {

  productListParams: ProductListParams = new ProductListParams();
  page: Page | null = null;
  brands: Brand[] = [];
  categories: Category[] = [];
  isError: boolean = false;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.retrieveProducts();
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

  numbers() {
    return Array.from(Array(this.page?.totalPages).keys());
  }

  changePage(newPage: number) {
    if(this.productListParams.page != newPage) {
      this.productListParams.page = newPage;
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

  applyClick() {
    this.productListParams.page = 1;
    this.retrieveProducts();
  }
}
