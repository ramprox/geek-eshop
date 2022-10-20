export class ProductFilter {
  public minPrice: number = -1;
  public maxPrice: number = -1;
  public brandIds: Set<number> = new Set<number>();
  public categoryId: string = '';
  public size: number = 10;
  public sortBy: string = 'id';
  public direction: string = 'asc';
  public page: number = 1;
}
