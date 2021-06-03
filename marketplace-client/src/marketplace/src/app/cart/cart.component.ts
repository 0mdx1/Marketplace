import {Component, Inject, OnInit} from '@angular/core';
import {CartLocalService} from "../_services/cart/cart-local.service";
import {Observable} from "rxjs";
import {CartModelServer} from "../_models/cart.model";
import {CartService} from "../_services/cart/cart.service";
import {CartItem} from "../_models/cart-item.model";
import {Product} from "../_models/product.model";
import {CartBrowserSyncService} from "../_services/cart/cart-browser-sync.service";

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  cart: CartItem[] = [];
  constructor(@Inject(CartBrowserSyncService) public cartService: CartService){}

  ngOnInit() {
    let cartItems: CartItem[] = this.cartService.getCartItems();
    if(cartItems.length == 0){
      cartItems = [
        {
          product: {
            id: 1,
            name: 'prod1',
            category: 'category1',
            description: 'description',
            image: 'image',
            price: 20,
            quantity: 30,
            images: "images",
          },
          quantity: 2,
          addingTime: Math.floor(Date.now() / 1000)
        }, {
          product:{
            id: 2,
            name: 'prod2',
            category: 'category1',
            description: 'description',
            image: 'image',
            price: 50,
            quantity: 100,
            images: "images",
          },
          quantity: 6,
          addingTime: Math.floor(Date.now() / 1000)
        }, {
          product:{
            id: 3,
            name: 'prod3',
            category: 'category1',
            description: 'description',
            image: 'image',
            price: 13,
            quantity: 100,
            images: "images",
          },
          quantity: 10,
          addingTime: Math.floor(Date.now() / 1000)}
      ]
    }
    this.cartService.setCartItems(cartItems);
    this.cart = this.cartService.getCartItems();
  }

  increaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.addProduct(cartItem.product);
  }

  decreaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.removeProduct(cartItem.product);
  }

  setQuantity(cartItem: CartItem, quantity: number): void {
    this.cartService.setProductQuantity(cartItem.product, quantity);
  }

  delete(cartItem: CartItem): void {
    this.cartService.deleteProduct(cartItem.product);
  }

  getSubtotalPrice(cartItem: CartItem): number {
    return cartItem.quantity*cartItem.product.price
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach( cartItem => {
      totalPrice+=this.getSubtotalPrice(cartItem);
    })
    return totalPrice;
  }


}
