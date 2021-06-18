import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { User } from "src/app/_models/user";
import { AccountService } from "src/app/_services/account.service";

@Component({
  templateUrl: './update.account.html'
})
export class UpdateAccount implements OnInit {

  updateForm: FormGroup;
  user: User = {};

  constructor(private formBuilder: FormBuilder, private accountService: AccountService) {
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
    
    this.accountService.updateUser(body).subscribe(
      data => console.log('Success ' + data),
      error => console.log(error)
    )
    
    
  }

}