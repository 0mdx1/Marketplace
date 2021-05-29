import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { MANAGERS, PAGE_NUM, PAGE_SIZE } from '../_helpers/mock-couriers';
import { User } from '../_models/user';
import { UserDto } from '../_models/UserDto';
import { CourierService } from './courier.service';

@Injectable({
  providedIn: 'root',
})
export class ManagerService {
  filter = '';
  search: string = '';
  page: number = 1;

  pageNumSource: BehaviorSubject<number> = new BehaviorSubject(0);
  pageSource: BehaviorSubject<number> = new BehaviorSubject(0);

  readonly ALL = 'all';
  readonly ENABLED = 'enabled';
  readonly DISABLED = 'disabled';

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  getManagers(): Observable<UserDto> {
    console.log('get Managers');

    if (!CourierService.isBlank(this.search)) {
      this.router.navigate(['/sysaccounts/managers'], {
        queryParams: {
          filter: this.filter,
          page: this.page,
          search: this.search,
        },
      });
    } else {
      this.router.navigate(['/sysaccounts/managers'], {
        queryParams: { filter: this.filter, page: this.page },
      });
    }

    let userDto = new UserDto();
    if (!CourierService.isBlank(this.search)) {
      userDto.users = MANAGERS.slice(0, 2);
      return of(userDto);
    }
    userDto.users = MANAGERS.slice(
      PAGE_SIZE * (this.page - 1),
      PAGE_SIZE * this.page
    );
    return of(userDto);
  }

  filterManagers(filter: string): Observable<UserDto> {
    if (
      filter != this.ALL &&
      filter != this.ENABLED &&
      filter != this.DISABLED
    ) {
      filter = this.ALL;
    }
    this.filter = filter;
    this.page = 1;
    return this.getManagers();
  }

  searchManagers(search: string): Observable<UserDto> {
    console.log('search managers service function');
    this.search = search;
    this.page = 1;
    return this.getManagers();
  }

  pageManagers(page: number): Observable<UserDto> {
    this.page = page;
    return this.getManagers();
  }

  getPage(): number {
    return Number(
      this.activatedRoute.snapshot.queryParamMap.get('page') || '1'
    );
  }

  getPageNum(): Observable<number> {
    return of(PAGE_NUM);
  }

  getSearch(): string {
    this.search =
      this.activatedRoute.snapshot.queryParamMap.get('search') || '';
    return this.search;
  }

  getFilters(): string {
    return this.activatedRoute.snapshot.queryParamMap.get('filter') || this.ALL;
  }

  initPage() {
    this.page = this.getPage();
  }

  getRequestParams(): HttpParams {
    if (CourierService.isBlank(this.search)) {
      return new HttpParams()
        .set('page', this.page.toString())
        .set('filter', this.filter);
    } else {
      return new HttpParams()
        .set('page', this.page.toString())
        .set('filter', this.filter)
        .set('search', this.search);
    }
  }
}
