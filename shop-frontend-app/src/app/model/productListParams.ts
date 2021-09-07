export class ProductListParams {
  public page: number = 1;
  public categoryId: number = -1;
  public productName: string = '';
  public minCost: number | null = null;
  public maxCost: number | null = null;
  constructor() {}
}
