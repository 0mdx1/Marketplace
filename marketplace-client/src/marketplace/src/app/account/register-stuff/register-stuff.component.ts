import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../_services/account.service";
import {validateBirthday} from "../../_helpers/validators.service";
import {first} from "rxjs/operators";
import {Role} from "../../_models/role";

@Component({
  selector: 'app-courier',
  templateUrl: './register-stuff.component.html',
  styleUrls: ['./register-stuff.component.css']
})
export class RegisterStuffComponent implements OnInit {


  form: FormGroup;
  submitted = false;

  roles: Role[] = [Role.Courier, Role.ProductManager]

  courierStatuses: string[] = ['Active','Inactive','Terminated']
  pmStatuses: string[] = ['Active','Terminated']

  // icons
  loading = false;
  showPassword = false;
  showConfirmPassword = false;
  registered = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
  ) {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.pattern(/\+380[0-9]{9}/)],
      birthday: [''],
      role: ['', Validators.required],
      status: ['', Validators.required]
    }, {
      validator: [validateBirthday]
    });
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  courierRoleSelected () : boolean {
    return this.form.value.role==Role.Courier;
  }

  pmRoleSelected () : boolean {
    return this.form.value.role==Role.ProductManager;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;


    let observable = null;
    if(this.courierRoleSelected()){
      observable = this.accountService.registerCourier(this.form.value);
    }else if (this.pmRoleSelected()){
      observable= this.accountService.registerProductManager(this.form.value);
    }else {
      return;
    }
    observable
      .pipe(first())
      .subscribe({
        next: () => {
          this.loading = false;
          this.registered = true;
        },
        error: error => {
          if (error.error.message === 'Email  already exists'){
            this.getForm.email.setErrors({EmailAlreadyExists : true});
          }
          this.loading = false;
        }
      });
  }

  ngOnInit(): void {
  }

}
