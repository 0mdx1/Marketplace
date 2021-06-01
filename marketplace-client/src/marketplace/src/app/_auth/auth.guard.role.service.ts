import { Injectable } from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot} from '@angular/router';
import { AuthService } from './auth.service';
import {Role} from "../_models/role";

@Injectable({
  providedIn: 'root',
})
export class RoleGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole: Role = route.data.roles;
    if (!this.auth.isAuthenticated() || !this.auth.isExpectedRole(expectedRole)) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
