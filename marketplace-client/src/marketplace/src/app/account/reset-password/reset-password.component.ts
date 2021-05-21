import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../../_services/account.service';
import {first} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {validateConfirmPassword, validatePassword} from '../../_helpers/validators.service';

@Component({
  selector: 'app-root',
  templateUrl: './reset-password.component.html',
})

export class ResetPasswordComponent {

  form: FormGroup;

  // icons
  loading = false;
  submitted = false;
  showPassword = false;
  showConfirmPassword = false;
  reseted = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private route: ActivatedRoute,
  ) {
    this.form = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
    }, {
       validator: [validateConfirmPassword, validatePassword]
    });
  }

  get getForm(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    this.submitted = true;
    this.loading = true;
    console.log('here');
    if (this.form.invalid) {
      return;
    }
    this.route.queryParamMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.accountService.setToken(this.route);
        this.accountService.setNewPassword(id, this.form.get('password')?.value)
          .pipe(first())
          .subscribe({
            next: () => {
              console.log('Password reset');
              this.loading = false;
              this.reseted = true;
            },
            error: (error: any) => {
              console.log(error);
              this.loading = false;
            }
          });
      }
    });
  }

  showHidePassword(): void {
    this.showPassword = !this.showPassword;
  }

  showHideConfirmPassword(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

}
