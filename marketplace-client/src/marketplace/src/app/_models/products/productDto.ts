import { Product } from './product';

export class ProductDto {
  current: number = 1;
  total: number = 1;
  result_set: Product[] = [];
}
