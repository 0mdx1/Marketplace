import {Component} from '@angular/core';

@Component(
  {
    templateUrl: 'create-password.component.html'}
)
export class CreatePasswordComponent{

  reseted = false;

  onSubmit(): void {
    this.reseted = true;
  }
}
