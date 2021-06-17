import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { PaginationComponent } from './pagination/pagination.component';
import {OrderComponent} from "./order/order.component";
import {OrderPageComponent} from "./order-list/order-page.component";


@NgModule({
  declarations: [
    PaginationComponent,
    OrderComponent,
    OrderPageComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule
  ]
})
export class OrderModule { }
