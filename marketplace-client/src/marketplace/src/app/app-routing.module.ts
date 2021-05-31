import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './home/home.component';
import {AuthGuardService} from './_auth/auth.guard.service';
import {Role} from "./_models/role";
import {RoleGuardService} from "./_auth/auth.guard.role.service";

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);

const routes: Routes = [
  //{ path: 'home', component: HomeComponent, canActivate: [RoleGuardService], data: { roles: [Role.Admin]}},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},
  { path: '', loadChildren: accountModule },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
