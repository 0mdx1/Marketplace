import {Component, Inject} from '@angular/core';

import { AccountService } from './_services/account.service';
import { User } from './_models/user';
import { Role } from './_models/role';
import {Product} from "./_models/products/product";
import {ProductComparisonService} from "./_services/product-comparison/product-comparison";
import {LimitedProductComparisonService} from "./_services/product-comparison/limited-product-comparison.service";

@Component({ selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']})
export class AppComponent {
  role = Role;
  // @ts-ignore
  user: User;

  comparedProd: Product[] = [];

  constructor(
    private accountService: AccountService,
    @Inject(LimitedProductComparisonService)private comparisonService: ProductComparisonService
  ) {
    this.comparedProd = comparisonService.getProducts();
    this.accountService.account.subscribe(x => this.user = x);
  }

/*  logout() {
    this.accountService.logout();
  }*/
}
