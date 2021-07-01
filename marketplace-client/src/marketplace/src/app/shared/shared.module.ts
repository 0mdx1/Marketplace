import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockPipe } from './_pipes/stock.pipe';

@NgModule({
  declarations: [StockPipe],
  imports: [CommonModule],
  exports: [StockPipe],
})
export class SharedModule {}
