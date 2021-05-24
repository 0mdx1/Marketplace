import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { MANAGERS, PAGE_NUM, PAGE_SIZE } from '../_helpers/mock-couriers';
import { User } from '../_models/user';
import { CourierService } from './courier.service';

@Injectable({
  providedIn: 'root',
})
export class ManagerService {
  filter = '';
  search: string = '';
  page: number = 1;

  readonly ALL = 'all';
  readonly ENABLED = 'enabled';
  readonly DISABLED = 'disabled';

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  getManagers(): Observable<User[]> {
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

    if (!CourierService.isBlank(this.search)) {
      return of(MANAGERS.slice(0, 2));
    }
    return of(
      MANAGERS.slice(PAGE_SIZE * (this.page - 1), PAGE_SIZE * this.page)
    );
  }

  filterManagers(filter: string): Observable<User[]> {
    if (
      filter != this.ALL &&
      filter != this.ENABLED &&
      filter != this.DISABLED
    ) {
      filter = this.ALL;
    }
    this.filter = filter;
    return this.getManagers();
  }

  searchManagers(search: string): Observable<User[]> {
    console.log('search managers service function');
    this.search = search;
    return this.getManagers();
  }

  pageManagers(page: number): Observable<User[]> {
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
}
