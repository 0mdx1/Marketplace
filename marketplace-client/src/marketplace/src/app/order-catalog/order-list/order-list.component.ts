import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {OrderPage} from "../../_models/order-info/order-page";
import {OrderService} from "../../_services/order.service";
import {Subscription} from "rxjs";
import {Product} from "../../_models/products/product";
import {CourierOrder} from "../../_models/order-info/courier-order";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent {
  @Input() orders: CourierOrder[] = [];

  constructor(private service: OrderService) {
  }

}
