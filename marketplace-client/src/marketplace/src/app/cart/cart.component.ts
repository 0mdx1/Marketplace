import {Component, OnInit} from '@angular/core';
import {CartService} from "../_services/cart/cart.service";
import {CartItem} from "../_models/cart-item.model";

@Component({
  selector: 'mg-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  items: CartItem[] = [];
  constructor(private cartService: CartService){}

  ngOnInit() {
    this.items = this.cartService.getCart().getItems();
  }

  public setTestItems() {
    let cartItems: CartItem[] = [
      {
        goods: {
          id: 1,
          name: 'prod1',
          category: 'category1',
          description: 'description',
          image: 'image',
          price: 20,
          quantity: 30,
        },
        quantity: 2,
        addingTime: Math.floor(Date.now() / 1000)
      }, {
        goods: {
          id: 2,
          name: 'prod2',
          category: 'category1',
          description: 'description',
          image: 'image',
          price: 50,
          quantity: 100,
        },
        quantity: 6,
        addingTime: Math.floor(Date.now() / 1000)
      }, {
        goods: {
          id: 3,
          name: 'prod3',
          category: 'category1',
          description: 'description',
          image: 'image',
          price: 13,
          quantity: 100,
        },
        quantity: 10,
        addingTime: Math.floor(Date.now() / 1000)
      }
    ]
    this.cartService.getCart().setItems(cartItems);
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
    return cartItem.quantity*cartItem.goods.price
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach( cartItem => {
      totalPrice+=this.getSubtotalPrice(cartItem);
    })
    return totalPrice;
  }


}
