import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-layout',
  templateUrl: './order-layout.component.html',
  styleUrls: ['./order-layout.component.css'],
})
export class OrderLayoutComponent {
  constructor(private router: Router) {}

  isUrlActive(urlPart: string): boolean {
    return this.router.url.includes(urlPart);
  }
}
