import { ProductFilterDto } from "./product-filter";

export class ProductListParams {
  public page: number = 1;
  public categoryId: number = -1;
  public productFilter: ProductFilterDto = new ProductFilterDto();
  constructor() {}
}
