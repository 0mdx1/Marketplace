import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StockPipe} from './_pipes/stock.pipe';
import {UnitPipe} from './_pipes/unit.pipe';
import {LoaderComponent} from './_components/loader/loader.component';

@NgModule({
  declarations: [StockPipe, UnitPipe, LoaderComponent],
  imports: [CommonModule],
  exports: [StockPipe, UnitPipe, LoaderComponent],
})
export class SharedModule {
}
