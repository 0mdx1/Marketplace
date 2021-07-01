export class ApiError{
  type: string;
  timestamp: string;
  message: string;

  constructor(type: string, timestamp: string, message: string) {
    this.type = type;
    this.timestamp = timestamp;
    this.message = message;
  }
}
