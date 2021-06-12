import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './_components/home/home.component';
import { AuthGuardService } from './_auth/auth.guard.service';
import { Role } from './_models/role';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { CartComponent } from './_components/cart/cart.component';

const accountModule = () =>
  import('./account/account.module').then(
    (x) => x.AccountModule
  );
const systemAccountModule = () =>
  import('./system-accounts/system-accounts.module').then(
    (x) => x.SystemAccountsModule
  );
const productCatalogModule = () =>
  import('./product-catalog/product-catalog.module').then(
    (x) => x.ProductCatalogModule
  );

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'accounts',
    loadChildren: accountModule
  },
  {
    path: 'cart',
    component: CartComponent
  },
  {
    path: 'sysaccounts',
    loadChildren: systemAccountModule,
    canActivate: [RoleGuardService],
    data: { roles: [Role.Admin] },
  },
  {
    path: 'products',
    loadChildren: productCatalogModule,
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'home'
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
