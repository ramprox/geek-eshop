import {CheckBrand} from "./check-brand";

export class ProductFilterDto {

  public namePattern: string | null = null;
  public minCost: number | null = null;
  public maxCost: number | null = null;
  public brands: Map<number, CheckBrand> = new Map();

  constructor() { }
}
