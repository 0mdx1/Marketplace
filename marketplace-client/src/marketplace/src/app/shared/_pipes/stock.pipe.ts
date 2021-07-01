import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'stock',
})
export class StockPipe implements PipeTransform {
  transform(value: boolean): string {
    return value ? 'In stock' : 'Out of stock';
  }
}
