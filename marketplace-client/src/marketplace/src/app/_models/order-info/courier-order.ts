import { CartItem } from '../cart-item.model';
import { UserDisplayInfo } from './userDisplayInfo';
import { Status } from '../status';
import { OrderProductInfo } from './orderProductInfo';

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
}
