import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { SystemAccountService } from 'src/app/_services/system-account.service';
import {AccountService} from "../../_services/account.service";
import {StaffMember} from "../../_models/staff-member";
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-courier',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  response: any;

  constructor(
    private accountService: SystemAccountService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(){
     //.subscribe((response) => {

        this.accountService.getManagerProfileInfo(this.route.snapshot.params.id)
          .subscribe((response) => {
            this.response=response;
            console.log(this.response);
          })
  }
}

