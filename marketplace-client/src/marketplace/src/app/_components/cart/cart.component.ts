import {Component, OnInit} from '@angular/core';
import {CartService} from "../../_services/cart/cart.service";
import {CartItem} from "../../_models/cart-item.model";
import {CartValidatorService} from "../../_services/cart/cart-validator.service";
import {catchError} from "rxjs/operators";
import {HttpErrorHandlerService} from "../../_services/http-error-handler.service";
import {Router} from "@angular/router";

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  items: CartItem[] = [];
  constructor(
    private cartService: CartService,
    private cartValidatorService: CartValidatorService,
    private errorHandler: HttpErrorHandlerService,
    private router: Router
  ){}

  ngOnInit() {
    this.items = this.cartService.getCart().getItems();
  }

  increaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.addProduct(cartItem.goods);
  }

  decreaseQuantityByOne(cartItem: CartItem): void {
    this.cartService.removeProduct(cartItem.goods);
  }

  setQuantity(cartItem: CartItem, quantity: number): void {
    this.cartService.setProductQuantity(cartItem.goods, quantity);
  }

  delete(cartItem: CartItem): void {
    this.cartService.deleteProduct(cartItem.goods);
  }

  getSubtotalPrice(cartItem: CartItem): number {
    if (cartItem.quantity < 0) cartItem.quantity = - cartItem.quantity;
    return cartItem.quantity*this.getPrice(cartItem);
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach( cartItem => {
      totalPrice+=this.getSubtotalPrice(cartItem);
    })
    return totalPrice;
  }

  getPrice(cartItem: CartItem): number{
    return cartItem.goods.price-cartItem.goods.price*(cartItem.goods.discount/100);
  }

  checkout() {
      this.cartValidatorService.validate(this.items)
        .pipe(
          catchError(err => {
            return this.errorHandler.displayValidationError(err);
          })
        )
        .subscribe(()=>{
          this.router.navigateByUrl('/checkout')
        })
  }
}
