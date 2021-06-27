import { Injectable } from '@angular/core';
import {SendRequestStrategy} from "./send-request-strategy";
import {Role} from "../../../_models/role";
import {AuthService} from "../../../_auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class SendRequestStrategyService implements SendRequestStrategy{
  constructor(private auth: AuthService) { }

  public allowedToSendRequests(): boolean {
    return this.auth.isAuthenticated()&&this.auth.isExpectedRole(Role.User);
  }

}
