import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './_components/home/home.component';
import { Role } from './_models/role';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { CartComponent } from './_components/cart/cart.component';
import { CheckoutComponent } from './_components/checkout/checkout.component';

const accountModule = () =>
  import('./account/account.module').then((x) => x.AccountModule);
const systemAccountModule = () =>
  import('./system-accounts/system-accounts.module').then(
    (x) => x.SystemAccountsModule
  );
const productCatalogModule = () =>
  import('./product-catalog/product-catalog.module').then(
    (x) => x.ProductCatalogModule
  );
const orderCatalogModule = () =>
  import('./order-catalog/order.module').then((x) => x.OrderModule);
const orderHistoryModule = () =>
  import('./order-history/order-history.module').then(
    (x) => x.OrderHistoryModule
  );

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'accounts',
    loadChildren: accountModule,
  },
  {
    path: 'cart',
    component: CartComponent,
    canActivate: [RoleGuardService],
    data: { roles: [Role.User, Role.AnonymousUser] },
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
    path: 'orders',
    loadChildren: orderCatalogModule,
    canActivate: [RoleGuardService],
    data: { roles: [Role.Courier] },
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    canActivate: [RoleGuardService],
    data: { roles: [Role.AnonymousUser, Role.User] },
  },
  {
    path: 'order-history',
    loadChildren: orderHistoryModule,
    canActivate: [RoleGuardService],
    data: { roles: [Role.User] },
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'home',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
