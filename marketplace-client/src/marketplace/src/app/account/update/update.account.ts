import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  templateUrl: './update.account.html'
})
export class UpdateAccount implements OnInit {

  updateForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.updateForm = this.formBuilder.group({
      name: [''],
      surname: [''],
      phone: ['', [Validators.pattern(/\+380[0-9]{9}/)]],
      birthday: ['']
    })
  }

  get getForm(): { [p: string]: AbstractControl } { return this.updateForm.controls; }

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  onSubmit() {
    const obj = {
      name: this.updateForm.value['name'],
      surname: this.updateForm.value['surname'],
      phone: this.updateForm.value['phone'],
      birthday: this.updateForm.value['birthday']
    }
    console.log(obj);
    
    
  }

}