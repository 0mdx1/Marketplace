import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OrderListComponent} from "./order-list/order-list.component";
import {OrderComponent} from "./order/order.component";

const routes: Routes = [
  { path: '', component: OrderListComponent },
  { path: ':id', component: OrderComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
