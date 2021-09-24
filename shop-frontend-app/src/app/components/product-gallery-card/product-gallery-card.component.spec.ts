import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductGalleryCardComponent } from './product-gallery-card.component';

describe('ProductGalleryCardComponent', () => {
  let component: ProductGalleryCardComponent;
  let fixture: ComponentFixture<ProductGalleryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductGalleryCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductGalleryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
