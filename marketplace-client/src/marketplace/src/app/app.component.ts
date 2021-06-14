import { Component } from '@angular/core';

import { AccountService } from './_services/account.service';
import {Account} from "./_models/account";

@Component({ selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']})
export class AppComponent {
  account: Account;

  constructor(private accountService: AccountService) {
    this.account = new Account();
    this.accountService.account.subscribe(x => this.account = x);
    console.log(this.account.email);
  }

 logout() {
    this.accountService.logout();
  }
}
