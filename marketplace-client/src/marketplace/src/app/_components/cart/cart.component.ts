import { Component, OnDestroy, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart/cart.service';
import { CartItem } from '../../_models/cart-item.model';
import { CartValidatorService } from '../../_services/cart/cart-validator.service';
import { Router } from '@angular/router';
import {
  PageEvent,
  PageMediatorService,
} from '../../_services/page-mediator.service';

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit, OnDestroy {
  items: CartItem[] = [];

  constructor(
    private cartService: CartService,
    private cartValidatorService: CartValidatorService,
    private mediator: PageMediatorService,
    private router: Router
  ) {}

  ngOnInit() {
    this.mediator.notify(this, PageEvent.PageArrive);
    this.items = this.cartService.getCart().getItems();
  }

  ngOnDestroy() {
    this.mediator.notify(this, PageEvent.PageLeave);
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
    if (cartItem.quantity < 0) cartItem.quantity = -cartItem.quantity;
    return cartItem.quantity * this.getPrice(cartItem);
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach((cartItem) => {
      totalPrice += this.getSubtotalPrice(cartItem);
    });
    return totalPrice;
  }

  getPrice(cartItem: CartItem): number {
    return (
      cartItem.goods.price -
      cartItem.goods.price * (cartItem.goods.discount / 100)
    );
  }

  checkout() {
    this.cartValidatorService.validate(this.items).subscribe({
      next: () => {
        this.router.navigateByUrl('/checkout');
      },
    });
  }
}
