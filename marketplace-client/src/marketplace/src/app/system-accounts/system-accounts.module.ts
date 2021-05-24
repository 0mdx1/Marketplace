import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourierListComponent } from './courier-list/courier-list.component';
import { SystemAccountsRoutingModule } from './system-accounts-routing-module';
import { CourierFilterComponent } from './courier-filter/courier-filter.component';
import { SystemAccountComponent } from './system-account/system-account.component';
import { ManagerListComponent } from './manager-list/manager-list.component';
import { PaginationComponent } from './pagination/pagination.component';
import { SearchComponent } from './search/search.component';
import { ManagerFilterComponent } from './manager-filter/manager-filter.component';

@NgModule({
  declarations: [
    CourierListComponent,
    CourierFilterComponent,
    SystemAccountComponent,
    ManagerListComponent,
    PaginationComponent,
    SearchComponent,
    ManagerFilterComponent,
  ],
  imports: [CommonModule, SystemAccountsRoutingModule],
  exports: [CourierListComponent],
})
export class SystemAccountsModule {}
