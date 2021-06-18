import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ConfirmedComponent } from './confirmed/confirmed.component';
import { CreatePasswordComponent } from './create-password/create-password.component';
import { ProfileComponent } from './profile/profile.component';
import { UpdateAccount } from './update/update.account';
import { UserOrdersComponent } from './user-orders/user-orders/user-orders.component';
import { OrderLayoutComponent } from './user-orders/order-layout/order-layout.component';
import { OrderDetailsComponent } from './user-orders/order-details/order-details.component';

const routes: Routes = [
  {
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'forgot-password', component: ForgotPasswordComponent },
      { path: 'reset-password', component: ResetPasswordComponent },
      { path: 'create-password', component: CreatePasswordComponent },
      { path: 'confirmed', component: ConfirmedComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'update-account', component: UpdateAccount},

      {
        path: 'orders',
        component: OrderLayoutComponent,
        children: [
          { path: 'incoming', component: UserOrdersComponent },
          { path: 'history', component: UserOrdersComponent },
          { path: ':orderId', component: OrderDetailsComponent },
        ],
      },
    ],
    path: '',
    component: LayoutComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AccountRoutingModule {}
