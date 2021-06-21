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
        this.cart.startPolling();
      }
      if(event==PageEvent.PageLeave){
        this.cart.stopPolling();
      }
    }
  }
}

export enum PageEvent{
  PageArrive,
  PageLeave
}
