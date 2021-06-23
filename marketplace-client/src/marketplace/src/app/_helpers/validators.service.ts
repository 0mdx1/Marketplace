import { AbstractControl } from '@angular/forms';

export function validateBirthday(control: AbstractControl): void {
  const ALLOWED_AGE_YEARS = 18;
  const birthday = control.get('birthday');
  const formYear = birthday?.value.split('-')[0];
  const formMonth = birthday?.value.split('-')[1];
  const formDay = birthday?.value.split('-')[2];
  const currentDate = new Date();
  if (
    birthday &&
    birthday.value &&
    (Number(formYear) + Number(ALLOWED_AGE_YEARS) > currentDate.getFullYear() ||
      formYear < 1920)
  ) {
    // person will be 18 y.o. at least next year
    birthday.setErrors({ InvalidDate: true });
  } else if (
    Number(formYear) + Number(ALLOWED_AGE_YEARS) == currentDate.getFullYear() &&
    Number(formMonth) > currentDate.getMonth() + 1
  ) {
    //person will be 18 y.o. this year but at least in a month
    birthday?.setErrors({ InvalidDate: true });
  } else if (
    Number(formYear) + Number(ALLOWED_AGE_YEARS) == currentDate.getFullYear() &&
    Number(formMonth) == currentDate.getMonth() + 1 &&
    Number(formDay) > currentDate.getDate()
  ) {
    //person will be 18 y.o. this year, this month but at least in a day
    birthday?.setErrors({ InvalidDate: true });
  }
}

export function validateShippingDate(control: AbstractControl): void {
  const shippingDate = control.get('shippingDate');
  let shippDate = new Date(
    shippingDate?.value.replace('T', ' ').replace(/-/g, '/')
  );
  if (new Date().getTime() - shippDate.getTime() < 0) {
    shippingDate?.setErrors({ InvalidDate: true });
  }
}

export function validateConfirmPassword(control: AbstractControl): void {
  const password = control.get('password')?.value;
  const confirmedPassword = control.get('confirmPassword')?.value;
  const passwordField = control.get('confirmPassword');
  if (passwordField && password !== confirmedPassword) {
    passwordField.setErrors({ NoPasswordMatch: true });
  }
}

export function validatePassword(control: AbstractControl): void {
  const password = control.get('password')?.value;
  const digitRegex = /\d/;
  const uppercaseRegex = /[A-Z]/;
  const lowercaseRegex = /[a-z]/;
  const passwordField = control.get('password');
  if (passwordField) {
    if (!digitRegex.test(password)) {
      passwordField.setErrors({ NoDigit: true });
    }
    if (!uppercaseRegex.test(password)) {
      passwordField.setErrors({ NoUppercase: true });
    }
    if (!lowercaseRegex.test(password)) {
      passwordField.setErrors({ NoLowercase: true });
    }
  }
}
