import {Inject, Injectable, OnDestroy, OnInit} from "@angular/core";

import {CartItem} from "../../_models/cart-item.model";
import {Cart} from "./cart";
import {PageCart} from "./page-cart";

@Injectable({
  providedIn: 'root'
})
export class BrowserCart implements Cart, OnDestroy{

  constructor(@Inject(PageCart)private cart: Cart) {
    this.cart.setItems(this.loadCartItems());
    window.addEventListener('storage',this.storageUpdateListener.bind(this));
  }

  private storageUpdateListener(): void{
    this.cart.setItems(this.loadCartItems());
  }

  ngOnDestroy(): void {
    window.removeEventListener('storage',this.storageUpdateListener.bind(this));
  }

  addItem(item: CartItem): void {
    this.cart.addItem(item);
    this.save(this.cart.getItems());
  }

  empty(): void {
    this.cart.empty();
    this.save(this.cart.getItems());
  }

  getItems(): CartItem[] {
    return this.cart.getItems();
  }

  removeItem(item: CartItem): void {
    this.cart.removeItem(item);
    this.save(this.cart.getItems());
  }

  setItems(items: CartItem[]): void {
    this.cart.setItems(items);
    this.save(this.cart.getItems());
  }

  updateItem(item: CartItem): void {
    this.cart.updateItem(item);
    this.save(this.cart.getItems());
  }

  getItem(productId: number): CartItem | null {
    return this.cart.getItem(productId);
  }


  private loadCartItems() {
    return JSON.parse(localStorage.getItem('cart') || null as any) || [];
  }

  private save(cart: CartItem[]): void {
    localStorage.setItem('cart', JSON.stringify(cart));
  }
}
