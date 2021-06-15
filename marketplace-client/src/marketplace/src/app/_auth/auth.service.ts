import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import Token from '../_models/jwt';
import {Role} from '../_models/role';
import decode from 'jwt-decode';

import jwtDecode from "jwt-decode";
import {Account} from "../_models/account";


@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(public jwtHelper: JwtHelperService) {}

  public isAuthenticated(): boolean {
    const token: string | null = localStorage.getItem('token');
    if (!token) {
      return false;
    }
    return !this.isExpired(token);
  }

  public isExpectedRole(expectedRole: Role): boolean {
    const token: string  | null = localStorage.getItem('token');
    if (!token){
      return false;
    }
    const decodedToken = decode<Token>(token);
    console.log(decodedToken.authorities[0]+ " " + expectedRole);
    return decodedToken.authorities[0] ==  expectedRole;
  }

  public isExpired(token: string): boolean {
    if (this.jwtHelper.isTokenExpired(token)) { localStorage.removeItem('token'); }
    return this.jwtHelper.isTokenExpired(token);
  }

  public getRole(): string | null {
    const token: string  | null = localStorage.getItem('token');
    if (!token){
      return null;
    }
    return decode<Token>(token).authorities[0];
  }

  public getAccount(): Account | null {
    const token: string  | null = localStorage.getItem('token');
    if (!token){
      return null;
    }
    const decodedToken = decode<Token>(token);
    const account = new Account(decodedToken.sub, decodedToken.authorities[0]);
    return account;
  }

  public getMail(): string | null {
    if (this.isAuthenticated()) {
      const token: string = String(localStorage.getItem('token'));
      if (!this.isExpired(token)) {
        return jwtDecode<Token>(token).sub;
      }
    }
    return null;
  }
}
