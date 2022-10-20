import {of} from "rxjs";
import {Injectable} from "@angular/core";
import {Category} from "../dto/category";

@Injectable({
  providedIn: 'root'
})
export class DynamicDatabase {

  dataMap = new Map<number | null, Category[]> ();

  constructor() {
    this.initData();
  }

  private initData() {
    let categories: Category[] = [];
    let category: Category = this.createCategory(1, 'Комплектующие к ПК', true);
    categories.push(category);
    category = this.createCategory(2, 'Мониторы', false);
    categories.push(category);
    category = this.createCategory(3, 'Планшеты', false);
    categories.push(category);
    category = this.createCategory(4, 'Принтеры', false);
    categories.push(category);
    category = this.createCategory(5, 'Смартфоны', false);
    categories.push(category);
    this.dataMap.set(null, categories);

    categories = [];
    category = this.createCategory(6, 'Видеокарты', false);
    categories.push(category);
    category = this.createCategory(7, 'Жесткие диски', false);
    categories.push(category);
    category = this.createCategory(8, 'Материнские платы', false);
    categories.push(category);
    category = this.createCategory(9, 'Оперативная память', false);
    categories.push(category);
    category = this.createCategory(10, 'Процессоры', false);
    categories.push(category);
    this.dataMap.set(1, categories);
  }

  private createCategory(id: number, name: string, hasChild: boolean) {
    let category: Category = new Category();
    category.id = id;
    category.name = name;
    category.hasChilds = hasChild;
    return category;
  }

  getChildren(categoryId: number | null) {
    return of(this.dataMap.get(categoryId));
  }

}
