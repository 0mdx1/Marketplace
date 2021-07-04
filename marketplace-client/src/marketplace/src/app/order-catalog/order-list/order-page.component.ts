import { Component, Input } from '@angular/core';
import { OrderPage } from '../../_models/order-info/order-page';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.css'],
})
export class OrderPageComponent {
  @Input() orderPage: OrderPage = new OrderPage();
}
