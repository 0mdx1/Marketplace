import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import Token from '../_models/jwt';
import {Role} from '../_models/role';
import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly token: string | null;

  constructor(public jwtHelper: JwtHelperService) {
     this.token = localStorage.getItem('token');
  }

  public isAuthenticated(): boolean {
    if (!this.token){
      return false;
    }
    return !this.isExpired(this.token);
  }

  public isExpectedRole(expectedRole: Role): boolean {
    if (!this.token){
      return false;
    }
    const decodedToken = decode<Token>(this.token);
    return decodedToken.role ===  expectedRole;
  }

  public isExpired(token: string): boolean {
    if (this.jwtHelper.isTokenExpired(token)) { localStorage.removeItem('token'); }
    return this.jwtHelper.isTokenExpired(token);
  }
}
