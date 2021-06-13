import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {OrderDto} from "../../_models/order-info/order-dto";
import {OrderService} from "../../_services/order.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit, OnDestroy {
  subscription!: Subscription;
  orderDto: OrderDto = new OrderDto();

  constructor(private service: OrderService) {
  }

  getOrders(): void {
    this.subscription = this.service.getOrders(this.orderDto.page)
      .subscribe((response: OrderDto) => {
          this.orderDto = response;
        }
      )
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.getOrders();
  }

  next(): void {
    this.orderDto.page = this.orderDto.page + 1;
    this.getOrders();
  }

  prev(): void {
    this.orderDto.page = this.orderDto.page - 1;
    this.getOrders();
  }

}
