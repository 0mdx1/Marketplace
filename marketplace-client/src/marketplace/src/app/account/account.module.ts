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
  ],
  providers: [PasswordFormComponent],
})
export class AccountModule {}
