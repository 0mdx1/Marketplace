import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderHistoryRoutingModule } from './order-history-routing.module';
import { UserOrdersComponent } from './user-orders/user-orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { OrderLayoutComponent } from './order-layout/order-layout.component';

@NgModule({
  declarations: [
    UserOrdersComponent,
    OrderDetailsComponent,
    OrderLayoutComponent,
  ],
  imports: [CommonModule, OrderHistoryRoutingModule],
})
export class OrderHistoryModule {}
