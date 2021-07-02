import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockPipe } from './_pipes/stock.pipe';
import { UnitPipe } from './_pipes/unit.pipe';

@NgModule({
  declarations: [StockPipe, UnitPipe],
  imports: [CommonModule],
  exports: [StockPipe, UnitPipe],
})
export class SharedModule {}
