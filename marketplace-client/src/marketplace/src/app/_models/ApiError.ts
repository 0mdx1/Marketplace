export class ApiError{
  status: String;
  errorType: String;
  timestamp: number;
  message: String;
  debugMessage: String;
  subErrors: any[];

  constructor(status: String, errorType: String, timestamp: number, message: String, debugMessage: String, subErrors: any[]) {
    this.status = status;
    this.errorType = errorType;
    this.timestamp = timestamp;
    this.message = message;
    this.debugMessage = debugMessage;
    this.subErrors = subErrors;
  }
}
