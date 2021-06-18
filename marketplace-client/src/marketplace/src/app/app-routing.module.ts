import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './_components/home/home.component';
import { AuthGuardService } from './_auth/auth.guard.service';
import { Role } from './_models/role';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { CartComponent } from './_components/cart/cart.component';
import { ImageUploadingComponent } from './file-uploading/_components/image-uploading/image-uploading.component';
import { ProductComparisonComponent } from './product-catalog/product-comparison/product-comparison.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { ProfileComponent } from './account/profile/profile.component';

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
    path: 'image-uploading',
    component: ImageUploadingComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
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
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
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
