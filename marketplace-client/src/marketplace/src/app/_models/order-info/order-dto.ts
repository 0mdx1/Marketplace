import {CourierOrder} from "./courier-order";

export class OrderDto {
  orders: CourierOrder[] = [];
  totalPages: number = 1;
  page: number = 1;
}
