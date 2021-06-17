import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OrderPageComponent} from "./order-list/order-page.component";
import {OrderComponent} from "./order/order.component";

const routes: Routes = [
  { path: '', component: OrderPageComponent },
  { path: ':id', component: OrderComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
