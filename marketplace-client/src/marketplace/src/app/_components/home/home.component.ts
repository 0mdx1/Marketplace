import { Component } from '@angular/core';
import {AccountService} from '../../_services/account.service';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent {

  constructor(private accountService: AccountService) {}

  logout(): void {
    this.accountService.logout();
  }

}
