export class ApiError{
  status: String;
  timestamp: number;
  message: String;
  debugMessage: String;
  subErrors: any[];

  constructor(status: String, timestamp: number, message: String, debugMessage: String, subErrors: any[]) {
    this.status = status;
    this.timestamp = timestamp;
    this.message = message;
    this.debugMessage = debugMessage;
    this.subErrors = subErrors;
  }
}
