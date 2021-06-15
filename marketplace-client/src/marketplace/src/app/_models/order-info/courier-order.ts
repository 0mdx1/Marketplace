import {CartItem} from "../cart-item.model";
import {UserDisplayInfo} from "./userDisplayInfo";
import {Status} from "../status";
import {OrderProductInfo} from "./orderProductInfo";

export class CourierOrder {
  id: number = 0;
  user: UserDisplayInfo | null = null;
  deliveryTime: string = ''; // not number
  status: Status | null = null;
  comment: string = '';
  disturb: boolean = false;
  totalSum: number = 0;
  discountSum: number = 0;
  goods: OrderProductInfo[] = [];
  address: string = '';
  // constructor(
  //   id: number,
  //   user: UserDisplayInfo,
  //   deliveryTime: string, // not number
  //   address: string,
  //   status: Status,
  //   comment: string,
  //   disturb: boolean,
  //   totalSum: number,
  //   discountSum: number, // why
  //   goods: OrderProductInfo[] = [] // why
  // ) {
  //   this.id = id;
  //   this.user = user;
  //   this.deliveryTime = deliveryTime;
  //   this.address = address;
  //   this.status = status;
  //   this.comment = comment;
  //   this.disturb = disturb;
  //   this.totalSum = totalSum;
  //   this.discountSum = discountSum;
  //   this.goods = goods; // is it ok
  // }
}
