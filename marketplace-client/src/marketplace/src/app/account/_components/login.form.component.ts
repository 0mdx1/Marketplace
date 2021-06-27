import {Component, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../_services/account.service';
import {environment} from "../../../environments/environment";
import {ApiError} from "../../_models/ApiError";
import {RecaptchaComponent} from "ng-recaptcha";



@Component({
    selector: 'app-login-form',
    templateUrl: 'login.form.component.html',
    styleUrls : ['login.form.component.css'],
  }
)
export class LoginFormComponent {
  form: FormGroup;
  submitted = false;

  // icons
  loading = false;
  showPassword = false;
  readonly siteKey = `${environment.captchaKey}`;
  @ViewChild(RecaptchaComponent) recaptcha!: RecaptchaComponent;
  captchaResponse: string = "";
  showCaptcha: boolean = false;

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

  resolvedCaptcha(captchaResponse: string){
    this.captchaResponse = captchaResponse;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
    this.form.disable();
    this.accountService.login(this.getForm.email.value, this.getForm.password.value,this.captchaResponse)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/home');
          this.submitted = false;
          this.loading = false;
          this.form.enable();
        },
          error: error => {
          this.recaptcha.reset();
          let apiError = error.error as ApiError;
          if(apiError!=null && apiError.errorType=='CAPTCHA_VALIDATION_ERROR'){
            this.showCaptcha=true;
          }
          this.loading = false;
          const passwordField = this.form.get('password');
          this.form.enable();
          if (passwordField) { passwordField.setErrors({IncorrectPassword: true}); }
        }
      });
  }

  showHidePassword(): void {
    this.showPassword = !this.showPassword;
  }
}
