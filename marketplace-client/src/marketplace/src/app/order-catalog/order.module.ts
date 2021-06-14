import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { PaginationComponent } from './pagination/pagination.component';
import {OrderComponent} from "./order/order.component";
import {OrderListComponent} from "./order-list/order-list.component";


@NgModule({
  declarations: [
    PaginationComponent,
    OrderComponent,
    OrderListComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule
  ]
})
export class OrderModule { }
