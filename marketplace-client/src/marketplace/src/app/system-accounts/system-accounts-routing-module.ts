import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CourierListComponent } from './courier-list/courier-list.component';
import { LayoutComponent } from './layout/layout.component';
import { ProdmanagersComponent } from './prodmanagers/prodmanagers.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: 'couriers', component: CourierListComponent },
      { path: 'managers', component: ProdmanagersComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemAccountsRoutingModule { }
