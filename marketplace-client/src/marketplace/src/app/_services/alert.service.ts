import { Injectable } from '@angular/core';
import {Alert, AlertType} from "../_models/alert";

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private alerts: Alert[] = [];

  constructor() { }

  public addAlert(message: String, type: AlertType): void{
    this.alerts.push(new Alert(message,type));
  }

  public removeAlert(alert: Alert): void{
    let index = this.alerts.indexOf(alert);
    if(index!=-1){
      this.alerts.splice(index,1);
    }
  }

  public getAlerts():Alert[] {
    return this.alerts;
  }

  public clear(): void{
    this.alerts.splice(0);
  }
}
