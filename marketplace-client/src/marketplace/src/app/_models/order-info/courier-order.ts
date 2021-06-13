import {CartItem} from "../cart-item.model";
import {UserDisplayInfo} from "./userDisplayInfo";

export interface CourierOrder {
  id: number;
  user: UserDisplayInfo;
  deliveryTime: string; // not number
  address: string;
  status: boolean;
  comment: string;
  disturb: boolean;
  totalSum: number;
  discountSum: number;
  goods: CartItem[];

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
