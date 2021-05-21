import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// import { LayoutComponent } from './layout.component';
// import { LoginComponent } from './login.component';
import { RegisterComponent } from './register/register.component';
import {LayoutComponent} from './layout/layout.component';
import {LoginComponent} from './login/login.component';
import {ForgotPasswordComponent} from './forgot-password/forgot-password.component';
// import { VerifyEmailComponent } from './verify-email.component';
// import { ForgotPasswordComponent } from './forgot-password.component';
// import { ResetPasswordComponent } from './reset-password.component';

const routes: Routes = [
  {
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
       { path: 'forgot-password', component: ForgotPasswordComponent },
      // { path: 'reset-password', component: ResetPasswordComponent }
    ],
    path: '', component: LayoutComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
