import {Component, Input, OnInit} from '@angular/core';
import {Brand} from "../../dto/brand";
import {BrandService} from "../../services/brand/brand.service";
import {Options} from "@angular-slider/ngx-slider";
import {ProductFilter} from "../../services/product/product-filter";
import {BrandCheckBox} from "../../model/brand-check-box";
import {MatCheckboxChange} from "@angular/material/checkbox";

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() public brands: BrandCheckBox[] = [];

  public minPrice: number = 0;

  public maxPrice: number = 200_000;

  private isPriceChanged: boolean = false;

  private isBrandReceived: boolean = false;

  public productFilter: ProductFilter = new ProductFilter();

  options: Options = {
    floor: this.minPrice,
    ceil: this.maxPrice,
    hidePointerLabels: true,
    hideLimitLabels: true,
    getSelectionBarColor: () => {return '#00FF00'},
  };

  constructor(private brandService: BrandService) { }

  ngOnInit(): void {
  }

  public getBrands(): void {
    if(this.isBrandReceived) {
      return;
    }
    this.brandService.getBrands()
      .subscribe({
        next: brand => {
          let brandCheckBox = new BrandCheckBox();
          brandCheckBox.brand = brand;
          this.brands.push(brandCheckBox);
        }
      });
    this.isBrandReceived = true;
    // this.brands = [
    //   new Brand(1, 'Samsung'),
    //   new Brand(2, 'LG')
    // ];
  }

  checkedChanged($event: MatCheckboxChange) {
    if($event.checked) {
      this.productFilter.brandIds.add(+$event.source.value);
    }
    this.productFilter.brandIds.delete(+$event.source.value);
  }

  reset() {
    this.productFilter = new ProductFilter();
    this.minPrice = 0;
    this.maxPrice = 200_000;
  }

  minPriceChanged() {
    if(!this.isPriceChanged) {
      this.isPriceChanged = true;
    }
    if(this.minPrice > this.maxPrice) {
      this.minPrice = this.maxPrice;
    }
    this.productFilter.minPrice = this.minPrice;
  }

  maxPriceChanged() {
    if(!this.isPriceChanged) {
      this.isPriceChanged = true;
    }
    if(this.maxPrice < this.minPrice) {
      this.maxPrice = this.minPrice;
    }
    this.productFilter.maxPrice = this.maxPrice;
  }
}
