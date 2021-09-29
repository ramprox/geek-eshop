import {Order} from "./order";
import {LineItem} from "./line-item";

export class OrderDetails {
  constructor(public orderDto: Order,
              public lineItems: LineItem[]) {}
}
