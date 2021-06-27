import {Inject, Injectable, OnDestroy} from '@angular/core';
import {PageCart} from "../page-cart";
import {Cart} from "../cart";
import {CartItem} from "../../../_models/cart-item.model";

@Injectable({
  providedIn: 'root'
})
export class CartTabSyncService implements OnDestroy{

  constructor(@Inject(PageCart)private cart: Cart) {
    this.cart.setItems(this.loadCartItems());
    window.addEventListener('storage',this.storageUpdateListener.bind(this));
  }

  ngOnDestroy(): void {
    window.removeEventListener('storage',this.storageUpdateListener.bind(this));
  }

  private storageUpdateListener(): void{
    this.cart.setItems(this.loadCartItems());
  }

  private loadCartItems() {
    return JSON.parse(localStorage.getItem('cart') || null as any) || [];
  }

  public save(cart: CartItem[]): void {
    localStorage.setItem('cart', JSON.stringify(cart));
  }
}
