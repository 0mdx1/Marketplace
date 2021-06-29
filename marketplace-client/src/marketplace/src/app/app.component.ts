import {Component, Inject, OnInit} from '@angular/core';

import { AccountService } from './_services/account.service';
import {Account} from "./_models/account";
import {Product} from "./_models/products/product";
import {ProductComparisonService} from "./_services/product-comparison/product-comparison";
import {LimitedProductComparisonService} from "./_services/product-comparison/limited-product-comparison.service";
import {CartService} from "./_services/cart/cart.service";
import {CartItem} from "./_models/cart-item.model";
import {CartInitService} from "./_services/cart/global/cart-init.service";

@Component({ selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']})
export class AppComponent implements OnInit{
  account: Account;
  comparedProd: Product[] = [];
  cartProd: CartItem[] = [];

  constructor(private accountService: AccountService,
              @Inject(LimitedProductComparisonService) private comparisonService: ProductComparisonService,
              private cartService: CartService,
              private cartInitService: CartInitService
  ) {
    this.account = new Account();
    this.accountService.account.subscribe(x => this.account = x);
    this.cartInitService.start();
  }

  logout() {
    this.accountService.logout();
  }

  ngOnInit(): void {
    this.comparedProd = this.comparisonService.getProducts();
    this.cartProd = this.cartService.getCart().getItems();
  }
}
