import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { OrderLayoutComponent } from './order-layout/order-layout.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';

const routes: Routes = [
  {
    path: '',
    component: OrderLayoutComponent,
    children: [
      { path: 'incoming', component: UserOrdersComponent },
      { path: 'previous', component: UserOrdersComponent },
      { path: ':orderId', component: OrderDetailsComponent },
      {
        path: '',
        pathMatch: 'prefix',
        redirectTo: 'incoming',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OrderHistoryRoutingModule {}
