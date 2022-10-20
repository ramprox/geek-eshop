import {Injectable} from '@angular/core';
import {ProductService} from "../product/product.service";
import {PictureService} from "../picture/picture.service";
import {ProductCard} from "../../model/product-card";
import {ProductFilter} from "../product/product-filter";
import {map, Observable} from "rxjs";
import {CartService} from "../cart/cart.service";

@Injectable({
  providedIn: 'root'
})
export class ProductCardService {

  constructor(private productService: ProductService,
              private pictureService: PictureService,
              private cartService: CartService) {
  }

  public getProductCards(name: string, productFilter: ProductFilter): Observable<ProductCard> {
    return this.productService.getProducts(name, productFilter)
      .pipe(map(productCard => {
        this.getImagesForProduct(productCard);
        return productCard;
      }));
  }

  public getProductCardsCart(): Observable<ProductCard> {
    return this.cartService.getProductsFromCart()
      .pipe(map(productCart => {
        let productCard: ProductCard = new ProductCard();
        this.productService.getProduct(+productCart.id)
          .subscribe({
            next: value => {
              this.getImagesForProduct(productCard);
              productCard = value;
            }
          })
        return productCard;
      }));
  }

  private getImagesForProduct(productCard: ProductCard) {
    this.pictureService.getImageLinksByProductId(productCard.listProduct.id)
      .subscribe({
        next: imageLinks => {
          productCard.imageLinks = imageLinks;
        }
      });
  }

}
