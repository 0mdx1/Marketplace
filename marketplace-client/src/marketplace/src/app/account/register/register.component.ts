import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AccountService } from '../../_services/account.service';
import { first } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import {
  validateBirthday,
  validateConfirmPassword,
  validatePassword,
} from '../../_helpers/validators.service';

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  form: FormGroup;
  submitted = false;

  // icons
  loading = false;
  showPassword = false;
  showConfirmPassword = false;
  registered = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService
  ) {
    this.form = this.formBuilder.group(
      {
        name: ['', Validators.required],
        surname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', Validators.pattern(/\+380[0-9]{9}/)],
        birthday: ['', Validators.required],
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(32),
          ],
        ],
        confirmPassword: ['', Validators.required],
        acceptTerms: [false, Validators.requiredTrue],
      },
      {
        validator: [
          validateConfirmPassword,
          validatePassword,
          validateBirthday,
        ],
      }
    );
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.form.disable();
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
    this.accountService
      .register(this.form.value)
      .pipe(first())
      .subscribe({
        next: () => {
          this.loading = false;
          this.form.enable();
          this.registered = true;
        },
        error: (error) => {
          if (error.error.message === 'Email  already exists') {
            this.getForm.email.setErrors({ EmailAlreadyExists: true });
          }
          this.loading = false;
          this.form.enable();
        },
      });
  }

  showHidePassword(): void {
    this.showPassword = !this.showPassword;
  }

  showHideConfirmPassword(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
}
