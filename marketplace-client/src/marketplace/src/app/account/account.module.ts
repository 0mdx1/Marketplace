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
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpConfigInterceptor } from '../_interceptor/httpconfig.interceptor';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { AuthGuardService } from '../_auth/auth.guard.service';
import { RoleGuardService } from '../_auth/auth.guard.role.service';
import { AuthService } from '../_auth/auth.service';

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
  ],
  providers: [PasswordFormComponent],
})
export class AccountModule {}
