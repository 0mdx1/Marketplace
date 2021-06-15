import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, shareReplay, tap } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';
import { ResetPasswordDTO } from '../_models/resetPasswordDTO';
import { StaffMember } from '../_models/staff-member';

const baseUrl = `${environment.apiUrl}`;

@Injectable({ providedIn: 'root' })
export class AccountService {
  private accountSubject: BehaviorSubject<User>;
  public account: Observable<User>;

  constructor(private router: Router, private http: HttpClient) {
    this.accountSubject = new BehaviorSubject<User>({} as any);
    this.account = this.accountSubject.asObservable();
  }

  public get accountValue(): User {
    return this.accountSubject.value;
  }

  login(email: string, password: string): Observable<HttpResponse<User>> {
    return this.http
      .post<User>(
        `${baseUrl}/login`,
        { email, password },
        { observe: 'response' }
      )
      .pipe(
        tap((res) => this.setToken(res)),
        shareReplay()
      );
  }

  logout(): void {
    this.accountSubject.next({} as any);
    localStorage.clear();
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
    return this.http.post(`${baseUrl}/reset-password`, email);
  }

  setNewPassword(link: string, password: string): Observable<any> {
    const body = new ResetPasswordDTO(link, password);
    return this.http.post(`${baseUrl}/setnewpassword`, body);
  }

  getUser(): Observable<User> {
    return this.http.get(`${baseUrl}/userinfo`);
  }

  setToken(authResult: any): void {
    const token = authResult.headers.get('Authorization');
    if (token) {
      localStorage.setItem('token', token);
    }
  }
}
