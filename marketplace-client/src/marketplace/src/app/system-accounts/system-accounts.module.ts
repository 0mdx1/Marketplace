import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourierListComponent } from './courier-list/courier-list.component';
import { SystemAccountsRoutingModule } from './system-accounts-routing-module';
import { SystemAccountComponent } from './system-account/system-account.component';
import { ManagerListComponent } from './manager-list/manager-list.component';
import { PaginationComponent } from './pagination/pagination.component';
import { SearchComponent } from './search/search.component';
import { FilterComponent } from './filter/filter.component';
import { RegisterStuffComponent } from './register-stuff/register-stuff.component';
import { ReactiveFormsModule } from '@angular/forms';
//import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    CourierListComponent,
    SystemAccountComponent,
    ManagerListComponent,
    PaginationComponent,
    SearchComponent,
    FilterComponent,
    RegisterStuffComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SystemAccountsRoutingModule,
    //NgbCollapseModule,
  ],
  exports: [CourierListComponent],
})
export class SystemAccountsModule {}
