import {Component} from '@angular/core';
import {Alert, AlertType} from "../../_models/alert";
import {AlertService} from "../../_services/alert.service";

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent{

  alerts: Alert[] = [];

  constructor(private alertService: AlertService) {
    this.alerts = alertService.getAlerts();
  }

  close(alert: Alert) {
    this.alertService.removeAlert(alert);
  }
}
