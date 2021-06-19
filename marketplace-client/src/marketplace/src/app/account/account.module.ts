import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { RegisterComponent } from './register/register.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ConfirmedComponent } from './confirmed/confirmed.component';
import { LoginFormComponent } from './_components/login.form.component';
import { CreatePasswordComponent } from './create-password/create-password.component';
import { PasswordFormComponent } from './_components/password.form.component';
import { ProfileComponent } from './profile/profile.component';
import { UpdateAccount } from './update/update.account';
import { UserOrdersComponent } from './user-orders/user-orders/user-orders.component';
import { OrderDetailsComponent } from './user-orders/order-details/order-details.component';
import { OrderLayoutComponent } from './user-orders/order-layout/order-layout.component';


@NgModule({
  imports: [CommonModule, ReactiveFormsModule, AccountRoutingModule],
  declarations: [
    LayoutComponent,
    RegisterComponent,
    LoginComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    ConfirmedComponent,
    LoginFormComponent,
    CreatePasswordComponent,
    PasswordFormComponent,
    ProfileComponent,
    UpdateAccount,
    UserOrdersComponent,
    OrderDetailsComponent,
    OrderLayoutComponent,
  ],
  providers: [PasswordFormComponent],
})
export class AccountModule {}
