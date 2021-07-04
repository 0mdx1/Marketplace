import {OrderItemModel} from './order-item.model';
import {FormGroup} from '@angular/forms';

export class OrderModel {
  name: string;
  surname: string;
  phone: string;
  address: string;
  deliveryTime: Date;
  comment: string;
  disturb: string;
  totalSum: number;
  discountSum: number;
  items: OrderItemModel[];

  constructor(items: OrderItemModel[],
              totalSum: number,
              discountSum: number,
              orderDetails: FormGroup,
              deliveryTime: Date
  ) {
    this.items = items;
    this.name = orderDetails.value.name;
    this.surname = orderDetails.value.surname;
    this.phone = orderDetails.value.phone;
    this.address = orderDetails.value.address;
    this.comment = orderDetails.value.comment;
    this.disturb = orderDetails.value.disturb;
    this.deliveryTime = deliveryTime;
    this.totalSum = totalSum;
    this.discountSum = discountSum;
  }
}
