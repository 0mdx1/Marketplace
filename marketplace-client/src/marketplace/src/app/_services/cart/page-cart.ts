import {Injectable} from '@angular/core';
import {CartItem} from "../../_models/cart-item.model";
import {Cart} from "./cart";

@Injectable({
  providedIn: 'root'
})


export class PageCart implements Cart {

  private cart: CartItem[] = [];

  constructor() {}

  addItem(item: CartItem): void {
    let index = this.getArrayIndex(item.goods.id);
    if(index!==-1){
      throw new Error("Illegal state: cart cannot contain items with same product id");
    }
    this.cart.push(item);
  }

  setItems(items: CartItem[]) {
    this.empty();
    items.forEach(item => {
      this.cart.push(item);
    })
  }

  getItem(productId: number): CartItem | null {
    let index = this.getArrayIndex(productId)
    if(index==-1){
      return null;
    }
    return this.cart[index];
  }

  getItems(): CartItem[] {
    return this.cart;
  }

  removeItem(item: CartItem): void {
    let index = this.getArrayIndex(item.goods.id);
    if(index==-1) return;
    this.cart.splice(index,1);
  }

  updateItem(item:CartItem): void {
    let index = this.getArrayIndex(item.goods.id);
    if(index==-1) return;
    this.cart[index]=item;
  }

  empty(): void {
    this.cart.splice(0);
  }

  private getArrayIndex(productId: number) {
    return this.cart.findIndex((cartItem) => {
      return cartItem.goods.id == productId;
    });
  }
}
