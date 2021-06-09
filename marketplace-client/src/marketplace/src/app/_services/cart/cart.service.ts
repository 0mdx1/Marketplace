import {Inject, Injectable} from '@angular/core';
import {CartItem} from "../../_models/cart-item.model";
import {PageCart} from "./page-cart";
import {GlobalCart} from "./global-cart";
import {Cart} from "./cart";
import {BrowserCart} from "./browser-cart";
import {Product} from "../../_models/products/product";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(@Inject(GlobalCart) private cart: Cart) {}

  getCart(): Cart {
    return this.cart;
  }

  addProduct(product: Product): void {
    let item = this.cart.getItem(product.id);
    if(item==null){
      this.cart.addItem(new CartItem(product,1,Math.floor(Date.now() / 1000)));
    }else{
      item.quantity++;
      this.cart.updateItem(item);
    }
  }

  removeProduct(product: Product): void {
    let item = this.cart.getItem(product.id);
    if(item==null){
      throw new Error("Cannot set remove a product than havent been added");
    }
    item.quantity--;
    if(item.quantity==0){
      this.cart.removeItem(item);
    }else {
      this.cart.updateItem(item);
    }
  }

  setProductQuantity(product: Product, quantity: number): void {
    let item = this.cart.getItem(product.id);
    if(item==null){
      throw new Error("Cannot set quantity for a product than havent been added");
    }
    item.quantity = quantity;
    this.cart.updateItem(item);
  }

  deleteProduct(product: Product): void {
    let item = this.cart.getItem(product.id);
    if(item==null){
      throw new Error("Cannot delete a product than havent been added");
    }
    this.cart.removeItem(item);
  }
}
