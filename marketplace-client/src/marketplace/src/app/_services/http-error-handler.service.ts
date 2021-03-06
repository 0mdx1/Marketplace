import { Injectable } from '@angular/core';
import { AlertService } from './alert.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertType } from '../_models/alert';
import { throwError } from 'rxjs';
import { ApiError } from '../_models/ApiError';

@Injectable({
  providedIn: 'root',
})
export class HttpErrorHandlerService {
  constructor(private alertService: AlertService) {}

  public displayValidationError(error: HttpErrorResponse) {
    let apiError: ApiError = <ApiError>error.error;
    this.alertService.addAlert(
      this.capitalizeFirstLetter(apiError.message),
      AlertType.Danger
    );
    return throwError(error);
  }

  private capitalizeFirstLetter(string: String) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}
