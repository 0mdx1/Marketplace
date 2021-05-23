import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../_services/account.service';

@Component({
    selector: 'app-login-form',
    templateUrl: 'login.form.component.html',
  }
)
export class LoginFormComponent {
  form: FormGroup;
  submitted = false;

  // icons
  loading = false;
  showPassword = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
  ) {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get getForm(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
    this.accountService.login(this.getForm.email.value, this.getForm.password.value)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/home');
          this.submitted = false;
          this.loading = false;
        },
          error: error => {
          console.log('Error: ' + error);
          this.loading = false;
          const passwordField = this.form.get('password');
          if (passwordField) { passwordField.setErrors({IncorrectPassword: true}); }
        }
      });
  }

  showHidePassword(): void {
    this.showPassword = !this.showPassword;
  }
}
