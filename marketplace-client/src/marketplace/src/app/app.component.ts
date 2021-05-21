import { Component } from '@angular/core';

import { AccountService } from './_services/account.services';
import { User } from './_models/user';
import { Role } from './_models/role';

@Component({ selector: 'app-root', templateUrl: 'app.component.html' })
export class AppComponent {
  role = Role;
  // @ts-ignore
  user: User;

  constructor(private accountService: AccountService) {
    this.accountService.account.subscribe(x => this.user = x);
  }

/*  logout() {
    this.accountService.logout();
  }*/
}
