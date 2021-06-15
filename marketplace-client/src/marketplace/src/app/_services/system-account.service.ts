import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserDto } from '../_models/UserDto';
import { ResetPasswordDTO } from '../_models/resetPasswordDTO';
import { StaffMember } from '../_models/staff-member';

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root',
})
export class SystemAccountService {
  pageNumSource: Subject<number> = new Subject();
  pageSource: Subject<number> = new Subject();

  readonly ALL = 'all';
  readonly ACTIVE = 'active';
  readonly INACTIVE = 'inactive';
  readonly TERMINATED = 'terminated';

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  private getUsers(
    filter: string,
    search: string,
    page: number
  ): Observable<UserDto> {
    this.addQueryParams(filter, search, page);
    //get method to backend api
    let url = baseUrl;
    if (this.router.url.includes('managers')) {
      url = url + '/manager';
    } else {
      url = url + '/courier';
    }
    return this.http.get<UserDto>(url, {
      params: this.buildQueryParams(filter, search, page),
    });
  }

  private buildQueryParams(
    filter: string,
    search: string,
    page: number
  ): HttpParams {
    if (!this.isBlank(search)) {
      //add search param only if it is not empty
      return new HttpParams()
        .set('filter', filter)
        .set('search', search)
        .set('page', page.toString());
    } else {
      return new HttpParams()
        .set('filter', filter)
        .set('page', page.toString());
    }
  }

  private addQueryParams(filter: string, search: string, page: number) {
    //filter = this.validateFilter(filter);
    let currentUrl = this.router.url.split('?')[0];
    if (!this.isBlank(search)) {
      //add search param only if it is not empty
      this.router.navigate([currentUrl], {
        queryParams: {
          filter: filter,
          page: page,
          search: search,
        },
      });
    } else {
      this.router.navigate([currentUrl], {
        queryParams: { filter: filter, page: page },
      });
    }
    return currentUrl;
  }

  getFilteredUsers(filter: string, init: boolean): Observable<UserDto> {
    let users;
    if (init) {
      users = this.getUsers(filter, this.getSearch(), this.getCurrentPage());
    } else {
      users = this.getUsers(filter, this.getSearch(), 1);
    }
    this.notifyPageComponent(users);
    return users;
  }

  getSearchedUsers(search: string, init: boolean): Observable<UserDto> {
    let users;
    if (init) {
      users = this.getUsers(this.getFilter(), search, this.getCurrentPage());
    } else {
      users = this.getUsers(this.getFilter(), search, 1);
    }
    this.notifyPageComponent(users);
    return users;
  }

  getPagedUsers(page: number): Observable<UserDto> {
    return this.getUsers(this.getFilter(), this.getSearch(), page);
  }

  getFilter(): string {
    return this.activatedRoute.snapshot.queryParamMap.get('filter') || this.ALL;
  }

  getCurrentPage(): number {
    return Number(this.activatedRoute.snapshot.queryParamMap.get('page') || 1);
  }

  getSearch() {
    return this.activatedRoute.snapshot.queryParamMap.get('search') || '';
  }

  navigateToRegisterStaff() {
    let currentUrl = this.router.url;
    let subpath = this.router.url.split('/');
    currentUrl = currentUrl.replace(
      subpath[subpath.length - 1],
      'register-stuff'
    );
    this.router.navigate([currentUrl]);
  }

  getStatusList(): string[] {
    let statusList = [this.ALL, this.ACTIVE, this.TERMINATED];
    if (this.router.url.includes('couriers')) {
      statusList.push(this.INACTIVE);
    }
    return statusList;
  }

  //checks whether string blank,null or undefined
  private isBlank(str: string): boolean {
    return !str || /^\s*$/.test(str);
  }

  private notifyPageComponent(users: Observable<UserDto>) {
    users.subscribe((res) => {
      this.pageNumSource.next(res.pageNum);
      this.pageSource.next(res.currentPage);
    });
  }

  getCourierProfileInfo(id: number) {
    return this.http.get(`${baseUrl}/courier/` + id);
  }

  getManagerProfileInfo(id: number) {
    return this.http.get(`${baseUrl}/manager/` + id);
  }

  updateCourierInfo(account: StaffMember, id: number): Observable<any> {
    return this.http.patch(`${baseUrl}/courier/` + id, account);
  }

  updateManagerInfo(account: StaffMember, id: number): Observable<any> {
    return this.http.patch(`${baseUrl}/manager/` + id, account);
  }

  navigateToUpdatedStaff(id: number, role: number) {
    let currentUrl = this.router.url;
    let subpath = this.router.url.split('/');
    currentUrl = currentUrl.replace(
      subpath[subpath.length - 1],
      'role/' + role + '/update-info/' + id
    );
    this.router.navigate([currentUrl]);
  }
}
/*{
    "dateOfBirth": "undefined",
    "email": "dwf6h@vmani.com",
    "id": 3,
    "name": "Artem",
    "phone": "+380934460974",
    "role": "ROLE_PRODUCT_MANAGER",
    "status": "inactive",
    "surname": "Krivoruchenko"
}*/
