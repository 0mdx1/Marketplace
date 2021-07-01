import { Injectable } from '@angular/core';
import { CartComponent } from '../_components/cart/cart.component';
import { CartPollingService } from './cart/global/cart-polling.service';

@Injectable({
  providedIn: 'root',
})
export class PageMediatorService {
  constructor(private cartPolling: CartPollingService) {}

  notify(sender: any, event: PageEvent): void {
    if (sender instanceof CartComponent) {
      if (event == PageEvent.PageArrive) {
        this.cartPolling.startPolling();
      }
      if (event == PageEvent.PageLeave) {
        this.cartPolling.stopPolling();
      }
    }
  }
}

export enum PageEvent {
  PageArrive,
  PageLeave,
}
