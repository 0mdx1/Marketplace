import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-system-account',
  templateUrl: './system-account.component.html',
  styleUrls: ['./system-account.component.css'],
})
export class SystemAccountComponent {
  constructor(private router: Router) {}
  isCollapsed = true;
  isActive(accountType: string): boolean {
    return this.router.url.includes(accountType);
  }
}
