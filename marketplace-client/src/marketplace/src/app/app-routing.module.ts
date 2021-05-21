import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './home/home.component';
import {AuthGuardService} from './_auth/auth.guard.service';

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
const systemAccountModule = () => import('./system-accounts/system-accounts.module').then(x => x.SystemAccountsModule);

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},
  { path: '', loadChildren: accountModule },
  { path: 'sysaccounts', loadChildren: systemAccountModule },
  // { path: 'admin', loadChildren: acanActivate: [AuthGuardService], data: { roles: [Role.Admin] } },

  // otherwise redirect to home
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
