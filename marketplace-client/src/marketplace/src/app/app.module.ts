import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './_components/home/home.component';
import { AuthService } from './_auth/auth.service';
import { AuthGuardService } from './_auth/auth.guard.service';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { RoleGuardService } from './_auth/auth.guard.role.service';
import { HttpConfigInterceptor } from './_interceptor/httpconfig.interceptor';
import { CartComponent } from './_components/cart/cart.component';
import { SystemAccountsModule } from './system-accounts/system-accounts.module';
import {
  BrowserAnimationsModule,
  NoopAnimationsModule,
} from '@angular/platform-browser/animations';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ProductCatalogModule } from './product-catalog/product-catalog.module';
import { ProductComparisonComponent } from './product-catalog/product-comparison/product-comparison.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { AlertComponent } from './_components/alert/alert.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { HttpErrorInterceptor } from './_interceptor/http-error-interceptor.service';
import { SharedModule } from './shared/shared.module';

@NgModule({
  imports: [
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserModule,
    SystemAccountsModule,
    ProductCatalogModule,
    SharedModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    NgxSpinnerModule,
    FormsModule,
    NgxSliderModule,
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    CartComponent,
    AlertComponent,
    ProductComparisonComponent,
    CheckoutComponent,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpConfigInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
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
