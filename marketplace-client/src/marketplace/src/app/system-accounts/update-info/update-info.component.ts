import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Role} from "../../_models/role";
import {SystemAccountService} from "../../_services/system-account.service";
import {validateBirthday} from "../../_helpers/validators.service";
import {StaffMember} from "../../_models/staff-member";

import {first} from "rxjs/operators";

@Component({
  selector: 'update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.css'],
})
export class UpdateInfoComponent implements OnInit{
  form: FormGroup;

  submitted = false;

  roles: Role[] = [Role.Courier, Role.ProductManager];

  courierStatuses: string[] = ['active', 'inactive', 'terminated'];

  pmStatuses: string[] = ['active', 'terminated'];

  loading = false;

  updated = false;

  response: any;


  ngOnInit(){
    //.subscribe((response) => {

    this.accountService.getManagerProfileInfo(this.route.snapshot.params.id)
      .subscribe((response) => {
        this.response=response;
        console.log(this.response);
      })
  }

  constructor(
    private formBuilder: FormBuilder,
    private accountService: SystemAccountService,
    private route: ActivatedRoute,
  ) {
    this.form = this.formBuilder.group(
      {
        name: ['', Validators.required],
        surname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', Validators.pattern(/\+380[0-9]{9}/)],
        birthday: [''],
        status: ['', Validators.required],
      },
      {
        validator: [validateBirthday],
      }
    );
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  courierRoleSelected(): boolean {
    return this.form.value.role == Role.Courier;
  }

  pmRoleSelected(): boolean {
    return this.form.value.role == Role.ProductManager;
  }

  private mapToStaffMember(o: any): StaffMember {
    return {
      id: -1,
      name: o.name,
      surname: o.surname,
      email: o.email,
      dateOfBirth: o.dateOfBirth,
      phone: o.phone,
      role: Role.ProductManager,
      status: o.status,
    };
  }

  onSubmit(): void {
    this.submitted = true;

    this.loading = true;
    let observable = null;

    observable = this.accountService.updateManagerInfo(
        this.mapToStaffMember(this.form.value)
      );

    console.log(this.mapToStaffMember(this.form.value))

    observable.pipe(first()).subscribe({
      next: () => {
        this.loading = false;
        this.updated = true;
      },
    });
  }
}
