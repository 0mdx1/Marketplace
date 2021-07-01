import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService } from '../../_services/account.service';
import { ApiError } from '../../_models/ApiError';
import { AlertType } from '../../_models/alert';
import { AlertService } from '../../_services/alert.service';

@Component({
  templateUrl: 'forgot-password.component.html',
  styleUrls: ['forgot-password.component.css'],
})
export class ForgotPasswordComponent {
  form: FormGroup;
  loading = false;
  submitted = false;
  sent = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private alertService: AlertService
  ) {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  // convenience getter for easy access to form fields
  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    // reset alerts on submit
    // this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }
    this.form.disable();
    this.loading = true;
    // this.alertService.clear();
    this.accountService
      .resetPassword(this.form.value)
      .pipe(first())
      .subscribe({
        next: () => {
          // this.router.navigate(['../registration-greeting', {relativeTo: this.route}]);
          this.loading = false;
          this.sent = true;
        },
        error: (error) => {
          let apiError = error.error as ApiError;
          console.log(error);
          if (apiError) {
            this.alertService.addAlert(apiError.message, AlertType.Danger);
          }
          if (error.status && error.status === 401) {
            this.getForm.email.setErrors({ EmailDoesNotExist: true });
          }
          this.loading = false;
          this.form.enable();
        },
      });
  }
}
