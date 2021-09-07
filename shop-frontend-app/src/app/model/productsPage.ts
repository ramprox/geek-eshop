import {Page} from "./page";
import {Brand} from "./brand";
import {Category} from "./category";

export class ProductsPage {

  constructor(public page: Page,
              public brands: Brand[],
              public categories: Category[]) {
  }
}
