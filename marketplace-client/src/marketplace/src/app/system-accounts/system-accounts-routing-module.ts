import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CourierListComponent } from './courier-list/courier-list.component';
import { ProdmanagersComponent } from './prodmanagers/prodmanagers.component';
import { SystemAccountComponent } from './system-account/system-account.component';

const routes: Routes = [
  {
    path: '', component: SystemAccountComponent,
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
