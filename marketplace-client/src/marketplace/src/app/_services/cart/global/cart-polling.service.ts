import {Inject, Injectable, OnDestroy} from '@angular/core';
import {interval, Subscription} from "rxjs";
import {CartHttpService} from "./cart-http.service";
import {BrowserCart} from "../browser/browser-cart";
import {Cart} from "../cart";
import {SendRequestStrategyService} from "./send-request-strategy.service";
import {SendRequestStrategy} from "./send-request-strategy";

@Injectable({
  providedIn: 'root'
})
export class CartPollingService implements OnDestroy{

  private subscription: Subscription | null = null;

  constructor(
    @Inject(BrowserCart)private cart: Cart,
    private httpService: CartHttpService,
    @Inject(SendRequestStrategyService)private sendRequestStrategy: SendRequestStrategy
  ) { }

  ngOnDestroy(): void {
    this.stopPolling();
  }

  public startPolling(): void {
    if(this.sendRequestStrategy.allowedToSendRequests()) {
      this.updateItems();
    }
    this.subscription = interval(10000)
      .subscribe(() => {
        if (this.sendRequestStrategy.allowedToSendRequests()) {
          this.updateItems();
        }
      });
  }

  private updateItems() {
    if (!document.hidden) {
      this.httpService.getShoppingCart()
        .subscribe({
          next: items => {
            if (JSON.stringify(items) !== JSON.stringify(this.cart.getItems())) {
              this.cart.setItems(items);
            }
          }
        })
    }
  }

  public stopPolling(): void{
    if(this.subscription!==null) {
      this.subscription.unsubscribe();
    }
  }
}
