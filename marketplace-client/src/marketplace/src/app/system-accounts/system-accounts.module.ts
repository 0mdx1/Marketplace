import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourierListComponent } from './courier-list/courier-list.component';
import { SystemAccountsRoutingModule } from './system-accounts-routing-module';
import { LayoutComponent } from './layout/layout.component';
import { ProdmanagersComponent } from './prodmanagers/prodmanagers.component';



@NgModule({
  declarations: [
    CourierListComponent,
    LayoutComponent,
    ProdmanagersComponent
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
