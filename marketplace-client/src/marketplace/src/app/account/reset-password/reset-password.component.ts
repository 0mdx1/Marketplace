import { Component } from '@angular/core';

@Component(
  {
    templateUrl: 'reset-password.component.html'}
)
export class ResetPasswordComponent{

  reseted = false;

  onSubmit(): void {
    this.reseted = true;
  }
}
