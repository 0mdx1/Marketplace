import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../_services/order.service";
import {CourierOrder} from "../../_models/order-info/courier-order";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  constructor(private service: OrderService){}

  public order?: CourierOrder; // is it ok?

  ngOnInit(): void {
    this.service.getOrder().subscribe((result: CourierOrder) => {
      this.order = result;
    });
  }

}
