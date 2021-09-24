import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ProductFilterDto } from "../../model/product-filter";
import { Brand } from "../../model/brand";
import { CheckBrand } from "../../model/check-brand";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<ProductFilterDto>();

  productFilter: ProductFilterDto = new ProductFilterDto();

  @Input() set brands(value: Brand[]) {
    let newMap: Map<number, CheckBrand> = new Map();
    for(var i = 0; i < value.length; i++) {
      let checkBrand = this.productFilter.brands.get(value[i].id);
      if(checkBrand != null) {
        newMap.set(value[i].id, checkBrand);
      } else {
        newMap.set(value[i].id, new CheckBrand(value[i], false));
      }
    }
    this.productFilter.brands = newMap;
  }

  get brands() : Brand[] {
    return Array.from(this.productFilter.brands.values()).map(item => item.brand);
  }

  constructor() { }

  ngOnInit(): void {
  }

  checkedChanged(id: number, event: Event): void {
    let checkBrand = this.productFilter.brands.get(id);
    if(checkBrand != null) {
      checkBrand.isChecked = (event.target as HTMLInputElement).checked;
    }
  }

  applyFilter() : void {
    this.filterApplied.emit(this.productFilter);
  }
}
