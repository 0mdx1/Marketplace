import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAccountsRoutingModule } from './system-accounts-routing-module';
import { SystemAccountComponent } from './system-account/system-account.component';
import { PaginationComponent } from './pagination/pagination.component';
import { SearchComponent } from './search/search.component';
import { FilterComponent } from './filter/filter.component';
import { RegisterStuffComponent } from './register-stuff/register-stuff.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { AccountListComponent } from './account-list/account-list.component';
import { UpdateInfoComponent } from './update-info/update-info.component';

@NgModule({
  declarations: [
    SystemAccountComponent,
    PaginationComponent,
    SearchComponent,
    FilterComponent,
    RegisterStuffComponent,
    AccountListComponent,
    UpdateInfoComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SystemAccountsRoutingModule,
    NgbCollapseModule,
  ],
  exports: [SystemAccountComponent],
})
export class SystemAccountsModule {}
