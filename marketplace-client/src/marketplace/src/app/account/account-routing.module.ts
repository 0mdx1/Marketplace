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
import { Role } from '../_models/role';
import { RoleGuardService } from '../_auth/auth.guard.role.service';

const routes: Routes = [
  {
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'forgot-password', component: ForgotPasswordComponent },
      { path: 'reset-password', component: ResetPasswordComponent },
      { path: 'create-password', component: CreatePasswordComponent },
      { path: 'confirmed', component: ConfirmedComponent },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [RoleGuardService],
        data: {
          roles: [Role.User, Role.Admin, Role.Courier, Role.ProductManager],
        },
      },
      {
        path: 'profile/edit',
        component: UpdateAccount,
        canActivate: [RoleGuardService],
        data: {
          roles: [Role.User, Role.Admin],
        },
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
