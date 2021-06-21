import {Component, EventEmitter, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../../_services/account.service';
import {first} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {validateConfirmPassword, validatePassword} from '../../_helpers/validators.service';
import {AlertService} from "../../_services/alert.service";
import {ApiError} from "../../_models/ApiError";
import {AlertType} from "../../_models/alert";

@Component({
  selector: 'app-password-form',
  templateUrl: './password.form.component.html',
})

export class PasswordFormComponent {

  @Output() onClick  = new EventEmitter();

  form: FormGroup;

  // icons
  loading = false;
  submitted = false;
  showPassword = false;
  showConfirmPassword = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private route: ActivatedRoute,
    private alertService: AlertService
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
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
    this.form.disable();
    this.route.queryParamMap.subscribe(params => {
      const id = params.get('link');
      if (id) {
        this.accountService.setNewPassword(id, this.form.get('password')?.value)
          .pipe(first())
          .subscribe({
            next: () => {
              this.loading = false;
              this.onClick.emit('reseted');
              this.form.enable();
            },
            error: (error: any) => {
              this.loading = false;
              let apiError = error.error as ApiError;
              if(apiError){
                this.alertService.addAlert(apiError.message,AlertType.Danger);
              }
              this.form.enable();
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
