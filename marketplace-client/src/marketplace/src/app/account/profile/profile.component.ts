import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SystemAccountService } from 'src/app/_services/system-account.service';
import { AccountService } from '../../_services/account.service';
import { StaffMember } from '../../_models/staff-member';
import { ActivatedRoute } from '@angular/router';
import { first, switchMap } from 'rxjs/operators';
import { User } from 'src/app/_models/user';
import { Subscription } from 'rxjs';
import { ApiError } from '../../_models/ApiError';
import { AlertType } from '../../_models/alert';
import { AlertService } from '../../_services/alert.service';

@Component({
  selector: 'app-courier',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user: User = {};
  subscription!: Subscription;
  birthday: string = '-';

  collapsedInfo: boolean = true;
  collapsedContact: boolean = false;

  loading: boolean = false;
  changedPassword: boolean = false;

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.subscription = this.accountService
      .getUser()
      .subscribe((response: User) => (this.user = response));
  }

  showHideInfo(): void {
    this.collapsedInfo = !this.collapsedInfo;
  }

  showHideContact(): void {
    this.collapsedContact = !this.collapsedContact;
  }

  logout(): void {
    this.accountService.logout();
  }

  changePassword(): void {
    this.loading = true;
    if (this.user && this.user.email) {
      this.accountService
        .resetPassword('{"email" : "' + this.user.email + '"}')
        .pipe(first())
        .subscribe({
          next: () => {
            this.loading = false;
            this.changedPassword = true;
          },
          error: (error) => {
            let apiError = error.error as ApiError;
            if (apiError) {
              this.alertService.addAlert(apiError.message, AlertType.Danger);
            }
            this.loading = false;
          },
        });
    }
  }
}
