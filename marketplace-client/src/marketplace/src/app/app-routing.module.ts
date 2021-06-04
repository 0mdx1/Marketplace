import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { AuthGuardService } from './_auth/auth.guard.service';
import { Role } from './_models/role';

const accountModule = () =>
  import('./account/account.module').then((x) => x.AccountModule);
const systemAccountModule = () =>
  import('./system-accounts/system-accounts.module').then(
    (x) => x.SystemAccountsModule
  );

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: '', loadChildren: accountModule },
  {
    path: 'sysaccounts',
    loadChildren: systemAccountModule,
    canActivate: [RoleGuardService],
    data: { roles: [Role.Admin] },
  },
  // { path: 'admin', loadChildren: acanActivate: [AuthGuardService], data: { roles: [Role.Admin] } },

  // otherwise redirect to home
  { path: '**', pathMatch: 'full', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
