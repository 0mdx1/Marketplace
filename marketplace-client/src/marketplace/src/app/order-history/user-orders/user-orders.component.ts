import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CourierOrder } from 'src/app/_models/order-info/courier-order';
import { OrderService } from 'src/app/_services/order.service';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.css'],
})
export class UserOrdersComponent implements OnInit, OnDestroy {
  orders: CourierOrder[] = [];
  subscription!: Subscription;

  constructor(private service: OrderService) {}

  ngOnInit(): void {
    this.subscription = this.service
      .getOrdersForUser()
      .subscribe((orders: CourierOrder[]) => (this.orders = orders));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
