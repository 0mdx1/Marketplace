import {Component, OnInit} from '@angular/core';
import {CartService} from "../_services/cart/cart.service";
import {CartItem} from "../_models/cart-item.model";

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  items: CartItem[] = [];
  constructor(private cartService: CartService){}

  ngOnInit() {
    this.items = this.cartService.getCart().getItems();
  }

  increaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.addProduct(cartItem.goods);
  }

  decreaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.removeProduct(cartItem.goods);
  }

  setQuantity(cartItem: CartItem, quantity: number): void {
    this.cartService.setProductQuantity(cartItem.goods, quantity);
  }

  delete(cartItem: CartItem): void {
    this.cartService.deleteProduct(cartItem.goods);
  }

  getSubtotalPrice(cartItem: CartItem): number {
    return cartItem.quantity*this.getPrice(cartItem);
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach( cartItem => {
      totalPrice+=this.getSubtotalPrice(cartItem);
    })
    return totalPrice;
  }

  getPrice(cartItem: CartItem): number{
    return cartItem.goods.price-cartItem.goods.price*(cartItem.goods.discount/100);
  }
}
