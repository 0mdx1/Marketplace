import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {OrderService} from "../../_services/order.service";
import {OrderPage} from "../../_models/order-info/order-page";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-pagination-order',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {

  orderPage: OrderPage = new OrderPage();
  subscription!: Subscription;

  @Output() notification: EventEmitter<void> = new EventEmitter<void>();

  constructor(private service: OrderService) {}

  private getOrders() {
    this.subscription =
      this.service.getPagedOrders(this.orderPage.page || 1)
        .subscribe((response: OrderPage) => {
          this.orderPage.orders = response.orders;
          // this.pages = new number[response.totalPages];
          this.notification.emit();
          this.orderPage.totalPages = response.totalPages;

        })
  }

  ngOnInit(): void {
    this.getOrders();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  next(): void {
    // @ts-ignore
    this.orderPage.page = this.orderPage.page + 1;
    this.getOrders();
  }

  prev(): void {
    // @ts-ignore
    this.orderPage.page = this.orderPage.page - 1;
    this.getOrders();
  }

}
