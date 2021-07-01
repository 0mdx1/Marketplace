import {Inject, Injectable, OnDestroy, OnInit} from "@angular/core";

import {CartItem} from "../../../_models/cart-item.model";
import {Cart} from "../cart";
import {PageCart} from "../page-cart";
import {CartTabSyncService} from "./cart-tab-sync.service";

@Injectable({
  providedIn: 'root'
})
export class BrowserCart implements Cart{

  constructor(
    @Inject(PageCart)private cart: Cart,
    private syncService: CartTabSyncService
  ) {}

  addItem(item: CartItem): void {
    this.cart.addItem(item);
    this.syncService.save(this.cart.getItems());
  }

  empty(): void {
    this.cart.empty();
    this.syncService.save(this.cart.getItems());
  }

  getItems(): CartItem[] {
    return this.cart.getItems();
  }

  removeItem(item: CartItem): void {
    this.cart.removeItem(item);
    this.syncService.save(this.cart.getItems());
  }

  setItems(items: CartItem[]): void {
    this.cart.setItems(items);
    this.syncService.save(this.cart.getItems());
  }

  updateItem(item: CartItem): void {
    this.cart.updateItem(item);
    this.syncService.save(this.cart.getItems());
  }

  getItem(productId: number): CartItem | null {
    return this.cart.getItem(productId);
  }
}
