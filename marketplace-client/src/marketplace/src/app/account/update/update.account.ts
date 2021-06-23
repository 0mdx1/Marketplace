import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { User } from "src/app/_models/user";
import { AccountService } from "src/app/_services/account.service";
import {Router} from "@angular/router";
import {first} from "rxjs/operators";
import {AlertType} from "../../_models/alert";
import {AlertService} from "../../_services/alert.service";
import {ApiError} from "../../_models/ApiError";

@Component({
  templateUrl: './update.account.html',
  styleUrls: ['./update.account.component.css'],
})
export class UpdateAccount implements OnInit {

  updateForm: FormGroup;
  user: User = {};

  collapsedInfo: boolean = true;
  collapsedContact: boolean = false;

  loading: boolean = false;
  changedPassword: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private accountService: AccountService,
              private router: Router,
              private alertService: AlertService) {
    this.updateForm = this.formBuilder.group({
      name: [''],
      surname: [''],
      phone: ['', [Validators.pattern(/\+380[0-9]{9}/)]],
      birthday: ['']
    });
  }

  get getForm(): { [p: string]: AbstractControl } { return this.updateForm.controls; }

  ngOnInit(): void {
    this.accountService.getUser().subscribe(data => {
      this.user = data
      this.updateForm.patchValue({
        name: this.user.name,
        surname: this.user.surname,
        phone: this.user.phone,
        birthday: this.user.dateOfBirth
      });
    });
  }

  onSubmit() {
    const body = {
      name: this.updateForm.value['name'],
      surname: this.updateForm.value['surname'],
      phone: this.updateForm.value['phone'],
      birthday: this.updateForm.value['birthday']
    }

    this.accountService.updateUser(body).subscribe({
      next: () => {
        this.router.navigate(['/accounts/profile']);
        this.alertService.addAlert("Profile was successfully updated!", AlertType.Success);
      },
      error: error => {
        let apiError = error.error as ApiError;
        if(apiError){
          this.alertService.addAlert(apiError.message,AlertType.Danger);
        }
        //this.alertService.addAlert("Error has occurred during updating", AlertType.Danger);
      }
    });
  }

  showHideInfo():void {
    this.collapsedInfo = !this.collapsedInfo;
  }

  showHideContact():void {
    this.collapsedContact = !this.collapsedContact;
  }

  logout(): void {
    this.accountService.logout();
  }

  changePassword(): void {
    this.loading = true;
    if(this.user && this.user.email) {
      this.accountService.resetPassword('{"email" : "' + this.user.email + '"}')
        .pipe(first())
        .subscribe({
          next: () => {
            this.loading = false;
            this.changedPassword = true;
          },
          error: error => {
            let apiError = error.error as ApiError;
            if(apiError){
              this.alertService.addAlert(apiError.message,AlertType.Danger);
            }
            this.loading = false;
          }
        });
    }
  }

}
