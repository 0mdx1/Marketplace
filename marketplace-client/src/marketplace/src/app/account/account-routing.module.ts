import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import {LayoutComponent} from './layout/layout.component';
import {LoginComponent} from './login/login.component';
import {ForgotPasswordComponent} from './forgot-password/forgot-password.component';
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {ConfirmedComponent} from './confirmed/confirmed.component';
import {AuthGuardService} from '../_auth/auth.guard.service';
import {RegisterStuffComponent} from "./register-stuff/register-stuff.component";

const routes: Routes = [
  {
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'register-stuff', component: RegisterStuffComponent},
      { path: 'forgot-password', component: ForgotPasswordComponent },
      { path: 'reset-password', component: ResetPasswordComponent},
      { path: 'confirmed', component: ConfirmedComponent}
    ],
    path: '', component: LayoutComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
