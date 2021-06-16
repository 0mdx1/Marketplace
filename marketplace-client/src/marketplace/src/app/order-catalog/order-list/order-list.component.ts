import {Component, Input} from '@angular/core';
import {CourierOrder} from "../../_models/order-info/courier-order";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent {
  @Input() orders: CourierOrder[] = [];
}
