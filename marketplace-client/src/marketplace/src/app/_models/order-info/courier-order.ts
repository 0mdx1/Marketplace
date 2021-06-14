import {CartItem} from "../cart-item.model";
import {UserDisplayInfo} from "./userDisplayInfo";
import {Status} from "../status";
import {OrderProductInfo} from "./orderProductInfo";

export class CourierOrder {
  id?: number;
  user?: UserDisplayInfo;
  deliveryTime?: string; // not number
  status?: Status;
  comment?: string;
  disturb?: boolean;
  totalSum?: number;
  discountSum?: number;
  goods?: OrderProductInfo[];
  address?: string;
  // constructor(
  //   id: number,
  //   user: User,
  //   deliveryTime: string, // not number
  //   address: string,
  //   status: boolean,
  //   comment: string,
  //   disturb: boolean,
  //   totalSum: number,
  //   discountSum: number, // why
  //   goods: CartItem[] = [] // why
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
