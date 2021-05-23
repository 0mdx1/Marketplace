import { Component } from '@angular/core';
import {AccountService} from '../_services/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './home.component.html',
})
export class HomeComponent {

  constructor(private accountService: AccountService) {}

  logout(): void {
    this.accountService.logout();
  }

}
