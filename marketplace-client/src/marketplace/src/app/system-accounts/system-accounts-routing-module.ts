import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SystemAccountComponent } from './system-account/system-account.component';
import { RegisterStuffComponent } from './register-stuff/register-stuff.component';
import { AccountListComponent } from './account-list/account-list.component';
import {ProfileComponent} from "./profile/profile.component";
import {UpdateInfoComponent} from "./update-info/update-info.component";

const routes: Routes = [
  {
    path: '',
    component: SystemAccountComponent,
    children: [
      { path: 'couriers', component: AccountListComponent },
      { path: 'managers', component: AccountListComponent },
      { path: 'register-stuff', component: RegisterStuffComponent },
      { path: 'profile/:id', component: ProfileComponent },
      { path: 'update-info/:id', component: UpdateInfoComponent },
      /*{
        path: '',
        pathMatch: 'prefix',
        redirectTo: 'couriers',
      },*/
      //{ path: 'couriers/:id', component: CouriersDetails },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SystemAccountsRoutingModule {}
