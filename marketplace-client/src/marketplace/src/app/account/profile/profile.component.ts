import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SystemAccountService } from 'src/app/_services/system-account.service';
import { AccountService } from '../../_services/account.service';
import { StaffMember } from '../../_models/staff-member';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { User } from 'src/app/_models/user';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-courier',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user?: User;
  subscription!: Subscription;
  birthday: string = '-';

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.subscription = this.accountService
      .getUser()
      .subscribe((response: User) => (this.user = response));

    //.subscribe((response) => {

    /*if(this.route.snapshot.params.role.localeCompare(1)) {
      this.accountService.getManagerProfileInfo(this.route.snapshot.params.id)
        .subscribe((response) => {
          this.response = response;
          console.log(this.response);
        })

    }

    else if (this.route.snapshot.params.role.localeCompare(2)){
      this.accountService.getCourierProfileInfo(this.route.snapshot.params.id)
        .subscribe((response) => {
          this.response = response;
          console.log(this.response);
        })
    }
    if (this.response.birthday == !null)
    this.birthday = this.response.birthday;*/
  }
}
