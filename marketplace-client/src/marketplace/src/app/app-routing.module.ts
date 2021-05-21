import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './home/home.component';
import {AuthGuard} from './_helpers/auth.guard';

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
// const adminModule = () => import('./admin/admin.module').then(x => x.AdminModule);
// const profileModule = () => import('./profile/profile.module').then(x => x.ProfileModule);

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: '', loadChildren: accountModule },
  // { path: 'profile', loadChildren: profileModule, canActivate: [AuthGuard] },
  // { path: 'admin', loadChildren: adminModule, canActivate: [AuthGuard], data: { roles: [Role.Admin] } },

  // otherwise redirect to home
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
