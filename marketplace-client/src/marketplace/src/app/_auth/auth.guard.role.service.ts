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
    const expectedRoles: Role[] = route.data.roles;
    let hasAccess: boolean = expectedRoles.findIndex(role => this.hasRole(role)) > -1;
    if (!hasAccess) {
      this.router.navigate(['accounts/login']);
      return false;
    }
    return true;
  }

  private hasRole(role: Role): boolean{
    if(Role.AnonymousUser==role&&!this.auth.isAuthenticated()){
      return true;
    }
    return this.auth.isAuthenticated()&&this.auth.isExpectedRole(role);
  }
}
