import { Injectable } from '@angular/core';
import {CartComponent} from "../_components/cart/cart.component";
import {GlobalCart} from "./cart/global-cart";

@Injectable({
  providedIn: 'root'
})
export class PageMediatorService {

  constructor(private cart: GlobalCart) { }

  notify(sender: any, event: PageEvent): void{
    if(sender instanceof CartComponent){
      if(event==PageEvent.PageArrive){
        console.log("Arrive to the cart page");
        this.cart.startPolling();
      }
      if(event==PageEvent.PageLeave){
        console.log("Leave from the cart page");
        this.cart.stopPolling();
      }
    }
  }
}

export enum PageEvent{
  PageArrive,
  PageLeave
}
