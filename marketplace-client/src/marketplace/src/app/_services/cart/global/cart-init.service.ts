import {Inject, Injectable, OnDestroy} from '@angular/core';
import {of, Subscription} from "rxjs";
import {switchMap} from "rxjs/operators";
import {BrowserCart} from "../browser/browser-cart";
import {Cart} from "../cart";
import {CartHttpService} from "./cart-http.service";
import {AuthService, AuthState} from "../../../_auth/auth.service";
import {SendRequestStrategyService} from "./send-request-strategy.service";
import {SendRequestStrategy} from "./send-request-strategy";

@Injectable({
  providedIn: 'root'
})
export class CartInitService implements OnDestroy{

  private subs: Subscription[] = [];

  private prevState: AuthState|null = null;

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
        .subscribe((state: AuthState)=>{
          if(state==AuthState.Authorized){
            this.init();
          }
          if(state==AuthState.Unauthorized&&this.prevState==AuthState.Authorized){
            this.cart.empty();
          }
          this.prevState = state;
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
