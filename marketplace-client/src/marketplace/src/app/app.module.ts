import {NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {AuthInterceptor} from './_auth/auth.interceptor';
import {AuthService} from './_auth/auth.service';
import {AuthGuardService} from './_auth/auth.guard.service';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {RoleGuardService} from './_auth/auth.guard.role.service';

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    HomeComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService, AuthGuardService, RoleGuardService, AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
