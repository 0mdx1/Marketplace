import {Injectable, OnInit} from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import Token from '../_models/jwt';
import {Role} from '../_models/role';
import decode from 'jwt-decode';

import jwtDecode from "jwt-decode";
import {Account} from "../_models/account";
import {BehaviorSubject, Observable, Subject} from "rxjs";


@Injectable({
  providedIn: 'root',
})
export class AuthService{

  private authState: BehaviorSubject<number>;

  accountSubject: BehaviorSubject<Account> = new BehaviorSubject(new Account());

  constructor(public jwtHelper: JwtHelperService) {
    this.getAccount();
    if(this.isAuthenticated()){
      this.authState = new BehaviorSubject(AuthState.Authorized);
    }else{
      this.authState = new BehaviorSubject(AuthState.Unauthorized)
    }
  }

  getAuthStateObs(): Observable<AuthState> {
    return this.authState.asObservable();
  }

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
    return decodedToken.authorities[0] ==  expectedRole;
  }

  public isExpired(token: string): boolean {
    if (
      this.jwtHelper.isTokenExpired(token)) { localStorage.removeItem('token');
      this.accountSubject.next(new Account());
    }
    return this.jwtHelper.isTokenExpired(token);
  }

  public getRole(): string | null {
    const token: string  | null = localStorage.getItem('token');
    if (!token){
      return null;
    }
    return decode<Token>(token).authorities[0];
  }

  public getAccount(){
    const token: string  | null = localStorage.getItem('token');
    if (token){
      const decodedToken = decode<Token>(token);
      this.accountSubject.next(new Account(decodedToken.sub, decodedToken.authorities[0]));
    }
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

  public setToken(token:string): void {
    if (token) {
      localStorage.setItem('token', token);
      const decodedToken = decode<Token>(token);
      const account = new Account(decodedToken.sub, decodedToken.authorities[0]);
      this.accountSubject.next(account);
      this.authState.next(AuthState.Authorized);
    }
  }

  public logout(): void {
    this.accountSubject.next(new Account());
    localStorage.clear();
    this.authState.next(AuthState.Unauthorized);
  }

}

export enum AuthState{
  Authorized,
  Unauthorized
}
