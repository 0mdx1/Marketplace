import {AbstractControl} from '@angular/forms';
//import * as moment from 'moment';

export function validateBirthday(control: AbstractControl): void{
  const birthday = control.get('birthday');
  const formYear = birthday?.value.split('-')[0];
  if (
    birthday &&
    birthday.value &&
    (formYear > new Date().getFullYear() || formYear < 1920)
  ) {
    birthday.setErrors({ InvalidDate: true });
  }
}

export function validateConfirmPassword(control: AbstractControl): void{
  const password = control.get('password')?.value;
  const confirmedPassword = control.get('confirmPassword')?.value;
  const passwordField = control.get('confirmPassword');
  if (passwordField && (password !== confirmedPassword)) {
    passwordField.setErrors({NoPasswordMatch: true});
  }
}

export function validatePassword(control: AbstractControl): void{
  const password = control.get('password')?.value;
  const digitRegex = /\d/;
  const uppercaseRegex = /[A-Z]/;
  const lowercaseRegex = /[a-z]/;
  const passwordField = control.get('password');
  if (passwordField) {
    if (!digitRegex.test(password)) {
      passwordField.setErrors({NoDigit: true});
    }
    if (!uppercaseRegex.test(password)) {
      passwordField.setErrors({NoUppercase: true});
    }
    if (!lowercaseRegex.test(password)) {
      passwordField.setErrors({NoLowercase: true});
    }
  }
}
