import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

<<<<<<< HEAD
import { HomeComponent } from './home/home.component';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { AuthGuardService } from './_auth/auth.guard.service';
import { Role } from './_models/role';
=======
import {HomeComponent} from './home/home.component';
import {AuthGuardService} from './_auth/auth.guard.service';
import {Role} from "./_models/role";
import {RoleGuardService} from "./_auth/auth.guard.role.service";
import {CartComponent} from "./cart/cart.component";
>>>>>>> cart

const accountModule = () =>
  import('./account/account.module').then((x) => x.AccountModule);
const systemAccountModule = () =>
  import('./system-accounts/system-accounts.module').then(
    (x) => x.SystemAccountsModule
  );

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: '', loadChildren: accountModule },
<<<<<<< HEAD
  {
    path: 'sysaccounts',
    loadChildren: systemAccountModule,
    canActivate: [RoleGuardService],
    data: { roles: [Role.Admin] },
  },
  // { path: 'admin', loadChildren: acanActivate: [AuthGuardService], data: { roles: [Role.Admin] } },

  // otherwise redirect to home
  { path: '**', pathMatch: 'full', redirectTo: 'login' },
=======
  { path: 'login', redirectTo: 'login' },
  {path: 'cart', component: CartComponent},
>>>>>>> cart
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
