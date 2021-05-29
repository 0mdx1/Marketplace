import { Courier } from './courier';

export class CourierDto {
  couriers: Courier[] = [];
  pageNum: number = 1;
  currentPage: number = 1;
}
