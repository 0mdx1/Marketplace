import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {shareReplay, tap} from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';
import { ResetPasswordDTO } from '../_models/resetPasswordDTO';
import { StaffMember } from '../_models/staff-member';
import { Account } from '../_models/account';
import { AuthService } from '../_auth/auth.service';

const baseUrl = `${environment.apiUrl}`;

@Injectable({ providedIn: 'root' })
export class AccountService {
  public account: Observable<User> = new Observable<User>();

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.account = this.authService.accountSubject.asObservable();
  }

  login(email: string, password: string, captchaResponse: string): Observable<HttpResponse<User>> {
    let httpHeaders = new HttpHeaders({'captcha-response': (captchaResponse ? captchaResponse : " ")});
    return this.http
      .post<User>(
        `${baseUrl}/login`,
        { email, password },
        {
          headers : httpHeaders,
          observe: 'response'
        }
      )
      .pipe(
        tap((res) => this.setToken(res)),
        shareReplay()
      );
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  register(account: User): Observable<User> {
    return this.http.post(`${baseUrl}/register`, account);
  }

  registerCourier(account: StaffMember): Observable<any> {
    return this.http.post(`${baseUrl}/courier`, account);
  }

  registerProductManager(account: StaffMember): Observable<any> {
    return this.http.post(`${baseUrl}/manager`, account);
  }

  resetPassword(email: string): Observable<any> {
    const header = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(`${baseUrl}/reset-password`, email, {headers:header});
  }

  setNewPassword(link: string, password: string): Observable<any> {
    const body = new ResetPasswordDTO(link, password);
    return this.http.post(`${baseUrl}/setnewpassword`, body);
  }

  updateUser(user: object): Observable<any> {
    return this.http.patch(`${baseUrl}/updateUserRole`, user);
  }

  getUser(): Observable<User> {
    return this.http.get(`${baseUrl}/userinfo`);
  }

  setToken(authResult: HttpResponse<User>): void {
    const token = authResult.headers.get('Authorization');
    if(token) this.authService.setToken(token);
  }
}
