import {Inject, Injectable, OnDestroy, OnInit} from "@angular/core";
import {interval, Subscription} from "rxjs";
import {takeWhile} from "rxjs/operators";

import {CartService} from "./cart.service";
import {Product} from "../../_models/product.model";
import {CartItem} from "../../_models/cart-item.model";
import {CartLocalService} from "./cart-local.service";

@Injectable({
  providedIn: 'root'
})
export class CartBrowserSyncService implements CartService, OnDestroy{

  private continuePolling: boolean = true;

  private subscription: Subscription = null as any;

  constructor(@Inject(CartLocalService)private cartService: CartService) {
    // this.subscription = interval(5000)
    //   .subscribe(()=>{
    //     console.log("polling...")
    //     this.cartService.setCartItems(this.loadCartItems())
    //   });
  }

  ngOnDestroy(): void {
    //this.subscription.unsubscribe();
  }

  addProduct(product: Product): void {
    this.cartService.addProduct(product);
    this.save(this.cartService.getCartItems());
  }

  deleteProduct(product: Product): void {
    this.cartService.deleteProduct(product);
    this.save(this.cartService.getCartItems());
  }

  getCartItems(): CartItem[] {
    let cartItems: CartItem[] = this.loadCartItems();
    if(cartItems.length>0){
      this.cartService.setCartItems(cartItems);
    }
    return this.cartService.getCartItems();
  }

  private loadCartItems() {
    return JSON.parse(localStorage.getItem('cart') || null as any) || [];
  }

  removeProduct(product: Product): void {
    this.cartService.removeProduct(product);
    this.save(this.cartService.getCartItems());

  }

  setCartItems(cartItems: CartItem[]): void {
    this.cartService.setCartItems(cartItems);
    this.save(this.cartService.getCartItems());
  }

  setProductQuantity(product: Product, quantity: number): void {
    this.cartService.setProductQuantity(product,quantity);
    this.save(this.cartService.getCartItems());
  }

  private save(cart: CartItem[]): void {
    localStorage.setItem('cart', JSON.stringify(cart));
  }
}
