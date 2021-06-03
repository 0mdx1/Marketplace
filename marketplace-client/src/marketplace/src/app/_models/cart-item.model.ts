import {Product} from "./product.model";

export class CartItem {
  product: Product;
  quantity: number;
  addingTime: number;

  constructor(product: Product, quantity: number, addingTime: number) {
    this.product = product;
    this.quantity = quantity;
    this.addingTime = addingTime;
  }
}
