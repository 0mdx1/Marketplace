import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {AuthInterceptor} from './_auth/auth.interceptor';
import {AuthService} from './_auth/auth.service';
import {AuthGuardService} from './_auth/auth.guard.service';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {RoleGuardService} from './_auth/auth.guard.role.service';
import {HttpConfigInterceptor} from './_interceptor/httpconfig.interceptor';
import {CartComponent} from "./cart/cart.component";
import { SystemAccountsModule } from './system-accounts/system-accounts.module';

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserModule,
    SystemAccountsModule,
    FormsModule
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    CartComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpConfigInterceptor,
      multi: true,
    },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    AuthGuardService,
    RoleGuardService,
    AuthService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
