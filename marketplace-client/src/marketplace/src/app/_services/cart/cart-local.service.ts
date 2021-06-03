import {Injectable} from '@angular/core';
import {Product} from "../../_models/product.model";
import {CartService} from "./cart.service";
import {CartItem} from "../../_models/cart-item.model";
import {CartModelPublic} from "../../_models/cart.model";

@Injectable({
  providedIn: 'root'
})


export class CartLocalService implements CartService {

  private cart: CartItem[] = [];

  constructor() {}

  addProduct(product: Product): void {
    let index = this.getArrayIndex(product);
    if(index==-1){
      this.cart[this.cart.length]=new CartItem(product,1, Math.floor(Date.now() / 1000))
    }else{
      this.cart[index].quantity++;
    }
  }

  setCartItems(cartItems: CartItem[]) {
    this.cart.splice(0);
    cartItems.forEach(cartItem => {
      this.cart.push(cartItem);
    })
  }

  getCartItems(): CartItem[] {
    return this.cart;
  }

  removeProduct(product: Product): void {
    let index = this.getArrayIndex(product);
    if(index==-1) return;
    if(this.cart[index].quantity>1){
      this.cart[index].quantity--;
    }else{
      this.cart.splice(index,1);
    }
  }

  setProductQuantity(product: Product, quantity: number): void {
    let index = this.getArrayIndex(product);
    if(index==-1) return;
    if(quantity>0){
      this.cart[index].quantity = quantity;
    }else if(quantity===0){
      this.cart.splice(index,1);
    }
  }

  deleteProduct(product: Product): void {
    let index = this.getArrayIndex(product);
    if(index==-1) return;
    this.cart.splice(index,1);
  }

  private getArrayIndex(product: Product) {
    return this.cart.findIndex((cartItem, i, cart) => {
      return cartItem.product.id == product.id;
    });
  }
}
