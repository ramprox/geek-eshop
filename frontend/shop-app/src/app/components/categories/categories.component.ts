import {Component, Input} from '@angular/core';
import {Category} from "../../dto/category";
import {CategoryService} from "../../services/category/category.service";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent {

  public categories: Category[] = [];

  @Input() public category: Category | null = null;

  @Input() public isRootNode: boolean = false;

  public isLoading: boolean = false;

  private dataLoaded: boolean = false;

  constructor(private categoryService: CategoryService) { }

  public getCategories(parentCategory: Category | null) {
    if(!this.dataLoaded) {
      this.isLoading = true;
      this.categoryService.getCategories(parentCategory ? parentCategory.id : null)
        .subscribe({
          next: category => {
            this.categories.push(category);
            this.isLoading = false;
            this.dataLoaded = true;
          }
        });
    }
  }

}
