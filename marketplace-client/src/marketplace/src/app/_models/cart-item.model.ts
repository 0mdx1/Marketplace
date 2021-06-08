import {Product} from "./product.model";

export class CartItem {
  goods: Product;
  quantity: number;
  addingTime: number;

  constructor(product: Product, quantity: number, addingTime: number) {
    this.goods = product;
    this.quantity = quantity;
    this.addingTime = addingTime;
  }
}
