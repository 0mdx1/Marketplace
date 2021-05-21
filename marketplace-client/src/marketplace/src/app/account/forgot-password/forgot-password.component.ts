import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { first, finalize } from 'rxjs/operators';

import { AccountService} from '../../_services/account.services';

@Component({ templateUrl: 'forgot-password.component.html' })
export class ForgotPasswordComponent{
  form: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
  ) {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  // convenience getter for easy access to form fields
  get getForm(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    this.submitted = true;

    // reset alerts on submit
    // this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    // this.alertService.clear();
    this.accountService.resetPassword(this.getForm.email.value)
      .pipe(first())
      .pipe(finalize(() => this.loading = false));
    // .subscribe({
    // next: () => this.alertService.success('Please check your email for password reset instructions'),
    // error: error => this.alertService.error(error)
    // });
  }
}
