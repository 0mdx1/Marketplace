import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import {
  COURIERS,
  SEARCH_COURIERS,
  PAGE_NUM,
  PAGE_SIZE,
} from '../_helpers/mock-couriers';
import { Courier } from '../_models/courier';
import { CourierDto } from '../_models/courierDto';

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

  pageNumSource: BehaviorSubject<number> = new BehaviorSubject(0);
  pageSource: BehaviorSubject<number> = new BehaviorSubject(0);

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  getCouriers(): Observable<CourierDto> {
    console.log('Request params: ' + this.getRequestParams().toString());
    console.log('Url: ' + this.router.url.split('?')[0]);

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

    let courierDto = new CourierDto();

    if (this.filter == this.ALL) {
      if (!CourierService.isBlank(this.search)) {
        courierDto.couriers = SEARCH_COURIERS;
        courierDto.currentPage = this.getPage();
        courierDto.pageNum = PAGE_NUM;
        return of(courierDto);
      }
      courierDto.couriers = COURIERS.slice(
        PAGE_SIZE * (this.page - 1),
        PAGE_SIZE * this.page
      );
      courierDto.currentPage = this.getPage();
      courierDto.pageNum = PAGE_NUM;
      return of(courierDto);
    }
    if (this.filter == 'active') {
      if (!CourierService.isBlank(this.search)) {
        courierDto.couriers = SEARCH_COURIERS;
        courierDto.currentPage = this.getPage();
        courierDto.pageNum = PAGE_NUM;
        return of(courierDto);
      }
      courierDto.couriers = COURIERS.filter((c) => c.active === true).slice(
        PAGE_SIZE * (this.page - 1),
        PAGE_SIZE * this.page
      );
      courierDto.currentPage = this.getPage();
      courierDto.pageNum = PAGE_NUM;
      return of(courierDto);
    } else {
      if (!CourierService.isBlank(this.search)) {
        courierDto.couriers = SEARCH_COURIERS;
        courierDto.currentPage = this.getPage();
        courierDto.pageNum = PAGE_NUM;
        return of(courierDto);
      }
      courierDto.couriers = COURIERS.filter((c) => c.active === false).slice(
        PAGE_SIZE * (this.page - 1),
        PAGE_SIZE * this.page
      );
      courierDto.currentPage = this.getPage();
      courierDto.pageNum = PAGE_NUM;
      return of(courierDto);
    }
  }

  filterCouriers(filter: string): Observable<CourierDto> {
    if (
      filter != this.ALL &&
      filter != this.ACTIVE &&
      filter != this.INACTIVE &&
      filter != this.TERMINATED
    ) {
      filter = this.ALL;
    }
    this.filter = filter;
    //every time as filter is changed current page should become 1
    //and new page num should be get
    this.page = 1;
    return this.getCouriers();
  }

  filterCouriersWithCurrentPage(filter: string): Observable<CourierDto> {
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

  searchCouriers(search: string): Observable<CourierDto> {
    this.search = search;
    this.page = 1;
    return this.getCouriers();
  }

  pageCouriers(page: number): Observable<CourierDto> {
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
    return this.activatedRoute.snapshot.queryParamMap.get('search') || '';
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

  initPage() {
    this.page = this.getPage();
  }

  initSearch() {
    this.search = this.getSearch();
  }

  //checks whether string blank,null or undefined
  static isBlank(str: string): boolean {
    return !str || /^\s*$/.test(str);
  }
}
