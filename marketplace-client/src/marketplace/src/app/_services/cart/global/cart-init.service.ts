import {Inject, Injectable, OnDestroy, OnInit} from '@angular/core';
import {Observable, of, Subscription} from "rxjs";
import {switchMap} from "rxjs/operators";
import {BrowserCart} from "../browser/browser-cart";
import {Cart} from "../cart";
import {CartHttpService} from "./cart-http.service";
import {AuthState, AuthService} from "../../../_auth/auth.service";
import {SendRequestStrategyService} from "./send-request-strategy.service";
import {SendRequestStrategy} from "./send-request-strategy";

@Injectable({
  providedIn: 'root'
})
export class CartInitService implements OnDestroy{

  private subs: Subscription[] = [];

  constructor(
    @Inject(BrowserCart)private cart: Cart,
    private httpService: CartHttpService,
    private authService: AuthService,
    @Inject(SendRequestStrategyService)private sendRequestStrategy: SendRequestStrategy
  ) {
  }

  ngOnDestroy(): void {
    this.subs.forEach((sub)=>{
      sub.unsubscribe();
    })
  }

  public start(): void{
    this.subs.push(
      this.authService.getAuthStateObs()
        .subscribe((event: AuthState)=>{
          if(event==AuthState.Authorized){
            this.init();
          }
          if(event==AuthState.Unauthorized){
            this.cart.empty();
          }
        }))
  }

  private init(): void{
    if(this.sendRequestStrategy.allowedToSendRequests()){
      if(this.cart.getItems().length>0){
        this.subs.push(
          this.httpService
            .putShoppingCart(this.cart.getItems())
            .subscribe()
        );
      }
      this.subs.push(
        this.httpService.getShoppingCart()
        .pipe(switchMap(items => {
          this.cart.setItems(items);
          return of({});
        }))
        .subscribe()
      );
      return;
    }
  }
}
