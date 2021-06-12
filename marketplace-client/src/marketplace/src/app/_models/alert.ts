export class Alert{
  message: String;
  type: AlertType;

  constructor(message: String, type: AlertType) {
    this.message = message;
    this.type = type;
  }
}

export enum AlertType{
  Primary = "primary",
  Secondary = "secondary",
  Success = "success",
  Danger = "danger",
  Warning = "warning",
  Info = "info",
  Light = "light",
  Dark = "dark",
}
