import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CourierOrder } from 'src/app/_models/order-info/courier-order';
import { OrderService } from 'src/app/_services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit, OnDestroy {
  constructor(private service: OrderService) {}

  order: CourierOrder = new CourierOrder();
  subscription!: Subscription;

  ngOnInit(): void {
    this.subscription = this.service
      .getOrder()
      .subscribe((response: CourierOrder) => {
        this.order = response;
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
