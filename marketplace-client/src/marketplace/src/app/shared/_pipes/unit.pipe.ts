import { Pipe, PipeTransform } from '@angular/core';
import { Unit } from 'src/app/_models/unit';

@Pipe({
  name: 'unit',
})
export class UnitPipe implements PipeTransform {
  transform(value: Unit | null, quantity: number): string {
    if (value) {
      let valueStr = value.toLowerCase();
      return quantity > 1 ? valueStr + 's' : valueStr;
    } else return '';
  }
}
