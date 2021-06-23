import { Unit } from '../unit';

export class OrderProductInfo {
  id: number = 0;
  name: string = '';
  category: string = '';
  firm: string = '';
  quantity: number = 0;
  price: number = 0;
  unit: Unit | null = null;
}
