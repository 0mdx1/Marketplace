import { Component, Inject} from '@angular/core';

import { AccountService } from './_services/account.service';
import {Account} from "./_models/account";
import {Product} from "./_models/products/product";
import {ProductComparisonService} from "./_services/product-comparison/product-comparison";
import {LimitedProductComparisonService} from "./_services/product-comparison/limited-product-comparison.service";

@Component({ selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']})
export class AppComponent {
  account: Account;

  comparedProd: Product[] = [];

  constructor(private accountService: AccountService,
              @Inject(LimitedProductComparisonService)private comparisonService: ProductComparisonService) {
    this.account = new Account();
    this.accountService.account.subscribe(x => this.account = x);
    this.comparedProd = comparisonService.getProducts();
    console.log(this.account.email);
  }

  logout() {
    this.accountService.logout();
  }
}
