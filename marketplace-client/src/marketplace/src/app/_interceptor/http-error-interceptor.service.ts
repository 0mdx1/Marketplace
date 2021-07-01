import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {catchError} from 'rxjs/operators';
import {ApiError} from "../_models/ApiError";
import {AlertType} from "../_models/alert";
import {AlertService} from "../_services/alert.service";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private alertService: AlertService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError((error: any) => {
        let apiError = error.error as ApiError;
        if(apiError){
          if(this.canBeAlerted(apiError.type)){
            this.alertService.addAlert(apiError.message,AlertType.Danger);
          }
          return throwError(apiError);
        }
        if(error.error.hasOwnProperty("message")){
          this.alertService.addAlert(error.error.message,AlertType.Danger);
          return throwError(new ApiError("general-0","",error.error.message));
        }
        this.alertService.addAlert("Unexpected error",AlertType.Danger);
        return throwError(new ApiError("general-0","","Unexpected error"));
      })
    )
  }

  canBeAlerted(type: string){
    return (type.match(/^general-\S+/gm)||[]).length>0
  }
}
