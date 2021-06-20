import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../_services/order.service";
import {CourierOrder} from "../../_models/order-info/courier-order";
import {Subscription} from "rxjs";
import {Status} from "../../_models/status";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})

export class OrderComponent implements OnInit {

  constructor(private service: OrderService) {}

  order: CourierOrder = new CourierOrder(); // is it ok?
  subscription!: Subscription;

  changeStatus() {
    this.service.changeStatus()
      .subscribe((response: Status) => {
        this.order.status = response;
      })
  }

  ngOnInit(): void {
    this.subscription =
      this.service.getOrder()
        .subscribe((response: CourierOrder) => {
          this.order = response;
        });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
