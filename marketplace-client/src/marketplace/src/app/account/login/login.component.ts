import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService } from '../../_services/account.services';

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent{
  form: FormGroup;
  loading = false;
  submitted = false;

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
      .pipe(first())
      .subscribe({
        next: () => {
          const returnUrl = 'home';
          this.router.navigateByUrl(returnUrl);
        },
        error: error => {
          console.log(error);
          this.loading = false;
        }
      });
  }
}
