import {Component, OnInit} from '@angular/core';
import {CartService} from "../_services/cart.service";
import {Observable} from "rxjs";
import {CartModelServer} from "../_models/cart.model";

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cartData: CartModelServer;
  cartTotal: number;
  subTotal: number;

  constructor(public cartService: CartService) {
    this.cartData = null as any;
    this.cartTotal = 0;
    this.subTotal = 0;
  }

  ngOnInit() {
     this.cartService.cartDataObs$.subscribe(data => this.cartData = data);
     this.cartService.cartTotal$.subscribe(total => this.cartTotal = total);
  }

  ChangeQuantity(id: number, increaseQuantity: Boolean) {
    this.cartService.UpdateCartData(id, increaseQuantity);
  }

}
