import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CourierOrder } from 'src/app/_models/order-info/courier-order';
import { User } from 'src/app/_models/user';
import { Checkout } from 'src/app/_services/checkout/checkout.service';
import { OrderService } from 'src/app/_services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit, OnDestroy {
  constructor(private service: OrderService) {}
  courier: User = {};
  order: CourierOrder = new CourierOrder();
  orderSubscription!: Subscription;
  userSubscription!: Subscription;

  ngOnInit(): void {
    this.orderSubscription = this.service
      .getUserOrder()
      .subscribe((response: CourierOrder) => {
        this.order = response;
      });
    this.userSubscription = this.service
      .getCourierInfo()
      .subscribe((user: User) => (this.courier = user));
  }

  ngOnDestroy(): void {
    this.orderSubscription.unsubscribe();
  }
}
