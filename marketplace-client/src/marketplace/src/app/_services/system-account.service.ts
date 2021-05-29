import { Injectable } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { MANAGERS, PAGE_NUM } from '../_helpers/mock-couriers';
import { UserDto } from '../_models/UserDto';

@Injectable({
  providedIn: 'root',
})
export class SystemAccountService {
  /*filter: string = '';
  search: string = '';
  page: number = 1;*/

  pageNumSource: Subject<number> = new Subject();
  pageSource: Subject<number> = new Subject();

  readonly ALL = 'all';
  readonly ACTIVE = 'active';
  readonly INACTIVE = 'inactive';
  readonly TERMINATED = 'disabled';

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  getUsers(filter: string, search: string, page: number): Observable<UserDto> {
    console.log(this.addQueryParams(filter, search, page));

    //get method to backend api
    let userDto = new UserDto();
    userDto.currentPage = page;
    userDto.pageNum = PAGE_NUM;
    userDto.users = MANAGERS;
    return of(userDto);
  }

  addQueryParams(filter: string, search: string, page: number): string {
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
      currentUrl =
        currentUrl +
        '?filter=' +
        filter +
        '&page=' +
        page +
        '&search=' +
        search;
    } else {
      this.router.navigate([currentUrl], {
        queryParams: { filter: filter, page: page },
      });
      currentUrl = currentUrl + '?filter=' + filter + '&page=' + page;
    }
    return currentUrl;
  }

  getFilteredUsers(filter: string, init: boolean): Observable<UserDto> {
    if (init) {
      return this.getUsers(filter, this.getSearch(), this.getCurrentPage());
    } else {
      return this.getUsers(filter, this.getSearch(), 1);
    }
  }

  getSearchedUsers(search: string, init: boolean): Observable<UserDto> {
    if (init) {
      return this.getUsers(this.getFilter(), search, this.getCurrentPage());
    } else {
      return this.getUsers(this.getFilter(), search, 1);
    }
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

  //checks whether string blank,null or undefined
  isBlank(str: string): boolean {
    return !str || /^\s*$/.test(str);
  }
}
