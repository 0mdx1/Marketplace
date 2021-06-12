import {Inject, Injectable} from '@angular/core';
import {AlertService} from "./alert.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Alert, AlertType} from "../_models/alert";
import {ErrorObserver, throwError} from "rxjs";
import {ApiError} from "../_models/ApiError";
import {BrowserCart} from "./cart/browser-cart";
import {toTitleCase} from "codelyzer/util/utils";

@Injectable({
  providedIn: 'root'
})
export class HttpErrorHandlerService {

  private test: String = "test";

  constructor(private alertService: AlertService) {
  }

  public displayValidationError(error: HttpErrorResponse){
    let apiError: ApiError = <ApiError>error.error;
    apiError.subErrors.forEach((subError: any)=>{
      this.alertService.addAlert(this.capitalizeFirstLetter(subError.message) ,AlertType.Danger);
    },this)
    return throwError(error);
  }

  private capitalizeFirstLetter(string : String) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}
