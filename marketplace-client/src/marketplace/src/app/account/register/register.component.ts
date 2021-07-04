import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AccountService } from '../../_services/account.service';
import { first } from 'rxjs/operators';
import {
  validateBirthday,
  validateConfirmPassword,
  validatePassword,
} from '../../_helpers/validators.service';
import { AlertService } from 'src/app/_services/alert.service';
import { AlertType } from 'src/app/_models/alert';

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  form!: FormGroup;
  submitted = false;

  loading = false;
  showPassword = false;
  showConfirmPassword = false;
  registered = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private alertService: AlertService
  ) {
    this.buildForm();
  }

  buildForm(): void {
    this.form = this.formBuilder.group(
      {
        name: ['', [Validators.required, Validators.maxLength(50)]],
        surname: ['', [Validators.required, Validators.maxLength(50)]],
        email: [
          '',
          [Validators.required, Validators.email, Validators.maxLength(50)],
        ],
        phone: ['', Validators.pattern(/^\+380[0-9]{9}$/)],
        birthday: ['', Validators.required],
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(100),
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
    if (this.form.invalid) {
      return;
    }
    this.form.disable();
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
          this.alertService.addAlert(error.message, AlertType.Danger);
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
