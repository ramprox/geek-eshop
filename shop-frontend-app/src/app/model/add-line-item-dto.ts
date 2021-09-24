export class AddLineItemDto {

  constructor(public productId: number,
              public qty: number,
              public color: string,
              public material: string) {
  }
}
