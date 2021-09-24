import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Page } from "../../model/page";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  @Input() page? : Page;

  @Output() pageNumberChanged = new EventEmitter<number>();

  pageNumber: number = 1;

  constructor() { }

  ngOnInit(): void {
  }

  pageClick(pageNumber: number) {
    if(this.pageNumber != pageNumber) {
      this.pageNumber = pageNumber;
      this.pageNumberChanged.emit(pageNumber);
    }
  }

  numbers() {
    return Array.from(Array(this.page?.totalPages).keys());
  }
}
