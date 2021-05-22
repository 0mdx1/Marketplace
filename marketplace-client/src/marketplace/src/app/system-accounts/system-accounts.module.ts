import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourierListComponent } from './courier-list/courier-list.component';
import { SystemAccountsRoutingModule } from './system-accounts-routing-module';
import { ProdmanagersComponent } from './prodmanagers/prodmanagers.component';
import { CourierFilterComponent } from './courier-filter/courier-filter.component';
import { SystemAccountComponent } from './system-account/system-account.component';



@NgModule({
  declarations: [
    CourierListComponent,
    ProdmanagersComponent,
    CourierFilterComponent,
    SystemAccountComponent
  ],
  imports: [
    CommonModule,
    SystemAccountsRoutingModule
  ],
  exports: [
    CourierListComponent
  ]
})
export class SystemAccountsModule { }
