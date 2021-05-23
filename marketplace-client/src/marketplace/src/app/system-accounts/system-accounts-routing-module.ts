import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CourierListComponent } from './courier-list/courier-list.component';
import { ManagerListComponent } from './manager-list/manager-list.component';
import { SystemAccountComponent } from './system-account/system-account.component';

const routes: Routes = [
  {
    path: '', component: SystemAccountComponent,
    children: [
      { path: 'couriers', component: CourierListComponent },
      { path: 'managers', component: ManagerListComponent},
      //{ path: 'couriers/:id', component: CouriersDetails },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemAccountsRoutingModule { }
