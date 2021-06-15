import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AccountService } from '../../_services/account.service';
import { validateBirthday } from '../../_helpers/validators.service';
import { first } from 'rxjs/operators';
import { Role } from '../../_models/role';
import { StaffMember } from '../../_models/staff-member';

@Component({
  selector: 'app-register-stuff',
  templateUrl: './register-stuff.component.html',
  styleUrls: ['./register-stuff.component.css'],
})
export class RegisterStuffComponent {
  form!: FormGroup;

  submitted = false;

  roles: Role[] = [Role.Courier, Role.ProductManager];

  courierStatuses: string[] = ['active', 'inactive', 'terminated'];

  pmStatuses: string[] = ['active', 'terminated'];

  loading = false;
  showPassword = false;
  showConfirmPassword = false;
  registered = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService
  ) {
    this.buildForm();
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

  private buildForm() {
    this.form = this.formBuilder.group(
      {
        name: ['', Validators.required],
        surname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', Validators.pattern(/\+380[0-9]{9}/)],
        birthday: [''],
        role: ['', Validators.required],
        status: ['', Validators.required],
      },
      {
        validator: [validateBirthday],
      }
    );
  }

  private mapToStaffMember(o: any): StaffMember {
    return {
      id: -1,
      name: o.name,
      surname: o.surname,
      email: o.email,
      dateOfBirth: o.dateOfBirth,
      phone: o.phone,
      role: o.role,
      status: o.status,
    };
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;

    let observable = null;
    if (this.courierRoleSelected()) {
      observable = this.accountService.registerCourier(
        this.mapToStaffMember(this.form.value)
      );
    } else if (this.pmRoleSelected()) {
      observable = this.accountService.registerProductManager(
        this.mapToStaffMember(this.form.value)
      );
    } else {
      return;
    }
    observable.pipe(first()).subscribe({
      next: () => {
        this.loading = false;
        this.registered = true;
      },
      error: (error) => {
        if (error.error.message === 'Email  already exists') {
          this.getForm.email.setErrors({ EmailAlreadyExists: true });
        }
        this.loading = false;
      },
    });
  }

  createAnotherUser() {
    this.loading = false;
    this.showPassword = false;
    this.showConfirmPassword = false;
    this.registered = false;
    this.submitted = false;

    this.buildForm();
  }
}
