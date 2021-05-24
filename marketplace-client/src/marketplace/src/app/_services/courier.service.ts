import { Injectable } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import {
  COURIERS,
  SEARCH_COURIERS,
  PAGE_NUM,
  PAGE_SIZE,
} from '../_helpers/mock-couriers';
import { Courier } from '../_models/courier';

@Injectable({
  providedIn: 'root',
})
export class CourierService {
  filter: string = '';
  search: string = '';
  page: number = 1;

  readonly ALL = 'all';
  readonly ACTIVE = 'active';
  readonly INACTIVE = 'inactive';
  readonly TERMINATED = 'disabled';

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  getCouriers(): Observable<Courier[]> {
    console.log('get Couriers search ' + this.search);

    if (!CourierService.isBlank(this.search)) {
      this.router.navigate(['/sysaccounts/couriers'], {
        queryParams: {
          filter: this.filter,
          page: this.page,
          search: this.search,
        },
      });
    } else {
      this.router.navigate(['/sysaccounts/couriers'], {
        queryParams: { filter: this.filter, page: this.page },
      });
    }

    if (this.filter == this.ALL) {
      if (!CourierService.isBlank(this.search)) {
        return of(SEARCH_COURIERS);
      }
      return of(
        COURIERS.slice(PAGE_SIZE * (this.page - 1), PAGE_SIZE * this.page)
      );
    }
    if (this.filter == 'active') {
      if (!CourierService.isBlank(this.search)) {
        return of(SEARCH_COURIERS);
      }
      return of(
        COURIERS.filter((c) => c.active === true).slice(
          PAGE_SIZE * (this.page - 1),
          PAGE_SIZE * this.page
        )
      );
    } else {
      if (!CourierService.isBlank(this.search)) {
        return of(SEARCH_COURIERS);
      }
      return of(
        COURIERS.filter((c) => c.active === false).slice(
          PAGE_SIZE * (this.page - 1),
          PAGE_SIZE * this.page
        )
      );
    }
  }

  filterCouriers(filter: string): Observable<Courier[]> {
    if (
      filter != this.ALL &&
      filter != this.ACTIVE &&
      filter != this.INACTIVE &&
      filter != this.TERMINATED
    ) {
      filter = this.ALL;
    }
    this.filter = filter;
    return this.getCouriers();
  }

  searchCouriers(search: string): Observable<Courier[]> {
    this.search = search;
    return this.getCouriers();
  }

  pageCouriers(page: number): Observable<Courier[]> {
    this.page = page;
    return this.getCouriers();
  }

  getFilters(): string {
    return this.activatedRoute.snapshot.queryParamMap.get('filter') || this.ALL;
  }

  getPage(): number {
    return Number(
      this.activatedRoute.snapshot.queryParamMap.get('page') || '1'
    );
  }

  getPageNum(): Observable<number> {
    console.log('get page num');
    return of(PAGE_NUM);
  }

  getSearch(): string {
    this.search =
      this.activatedRoute.snapshot.queryParamMap.get('search') || '';
    return this.search;
  }

  //checks whether string blank,null or undefined
  static isBlank(str: string): boolean {
    return !str || /^\s*$/.test(str);
  }
}
