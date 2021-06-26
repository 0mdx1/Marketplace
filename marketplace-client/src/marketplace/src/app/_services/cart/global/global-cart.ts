import {Inject, Injectable, OnDestroy} from "@angular/core";
import {interval, Observable, of, Subscription} from "rxjs";

import {CartItem} from "../../../_models/cart-item.model";
import {Cart} from "../cart";
import {BrowserCart} from "../browser/browser-cart";
import {switchMap} from "rxjs/operators";
import {AlertService} from "../../alert.service";
import {ApiError} from "../../../_models/ApiError";
import {AlertType} from "../../../_models/alert";
import {CartHttpService} from "./cart-http.service";
import {SendRequestStrategy} from "./send-request-strategy";
import {SendRequestStrategyService} from "./send-request-strategy.service";


@Injectable({
  providedIn: 'root'
})
export class GlobalCart implements Cart{

  private subs: Subscription[] = [];

  private initialized: boolean = false;

  constructor(
    @Inject(BrowserCart)private cart: Cart,
    private httpService: CartHttpService,
    private alertService: AlertService,
    @Inject(SendRequestStrategyService)private sendRequestStrategy: SendRequestStrategy
  ) {
  }

  ngOnDestroy(): void {
    this.subs.forEach((sub)=>{
      sub.unsubscribe();
    })
  }

  addItem(item: CartItem): void {
    this.cart.addItem(item);
    if(this.sendRequestStrategy.allowedToSendRequests()){
      this.subs.push(
        this.httpService.putShoppingCartItem(item)
        .subscribe({error: e=>{
            let apiError = e.error as ApiError;
            if(apiError){
              this.alertService.addAlert(apiError.message,AlertType.Danger);
            }
        }})
      );
    }
  }

  empty(): void {
    this.cart.empty();
    if(this.sendRequestStrategy.allowedToSendRequests()) {
      this.subs.push(
        this.httpService.deleteShoppingCart()
          .subscribe({error: e => {
              let apiError = e.error as ApiError;
              if(apiError){
                this.alertService.addAlert(apiError.message,AlertType.Danger);
              }
            }})
      )
    }
  }



  getItems(): CartItem[] {
    return this.cart.getItems();
  }

  removeItem(item: CartItem): void {
    this.cart.removeItem(item);
    if(this.sendRequestStrategy.allowedToSendRequests()) {
      this.subs.push(
        this.httpService.deleteShoppingCartItem(item)
          .subscribe({error: e => {
              let apiError = e.error as ApiError;
              if(apiError){
                this.alertService.addAlert(apiError.message,AlertType.Danger);
              }
            }})
      );
    }
  }



  setItems(items: CartItem[]): void {
    this.cart.setItems(items);
    if(this.sendRequestStrategy.allowedToSendRequests()) {
      this.subs.push(
        this.httpService.putShoppingCart(items)
          .subscribe({
            error: e=>{
              let apiError = e.error as ApiError;
              if(apiError){
                this.alertService.addAlert(apiError.message,AlertType.Danger);
              }
            }
          })
      );
    }
  }

  updateItem(item: CartItem): void {
    this.cart.updateItem(item);
    if(this.sendRequestStrategy.allowedToSendRequests()) {
      this.subs.push(
        this.httpService.patchShoppingCartItem(item)
          .subscribe({error: e => {
              let apiError = e.error as ApiError;
              if(apiError){
                this.alertService.addAlert(apiError.message,AlertType.Danger);
              }
            }})
      );
    }
  }

  getItem(productId: number): CartItem | null {
    return this.cart.getItem(productId);
  }
}
