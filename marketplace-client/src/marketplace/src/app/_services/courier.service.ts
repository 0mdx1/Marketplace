import { Injectable } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { COURIERS, ACTIVE_COURIERS, INACTIVE_COURIERS, SEARCH_COURIERS, PAGE_NUM, PAGE_SIZE } from '../_helpers/mock-couriers';
import { Courier } from '../_models/courier';

@Injectable({
  providedIn: 'root'
})
export class CourierService {
  filterBy: string = "";
  readonly ALL = "all";
  readonly ACTIVE = "active";
  readonly INACTIVE = "inactive";

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  getCouriers(): Observable<Courier[]> {
    return of(COURIERS);
  }

  filterCouriers(filter: string, search: string, page: number): Observable<Courier[]> {
    console.log('search' + search);
    if(!this.isBlank(search)) {
      this.router.navigate(['/couriers'], { queryParams:{'filter': filter, 'page': page, 'search': search} });
    } else {
      this.router.navigate(['/couriers'], { queryParams:{'filter': filter, 'page': page} });
    }


    if(filter == 'all') {
      if(!this.isBlank(search)) {
        return of(SEARCH_COURIERS);
      }
      return of(COURIERS.slice(PAGE_SIZE*(page-1), PAGE_SIZE*page));
    } if(filter == 'active') {
      if(!this.isBlank(search)) {
        return of(SEARCH_COURIERS);
      }
      return of(COURIERS.filter(c => c.active===true));

    } else {
      if(!this.isBlank(search)) {
        return of(SEARCH_COURIERS);
      }
      return of(COURIERS.filter(c => c.active===false));
    }
  }

  getFilters(): string {
    return this.activatedRoute.snapshot.queryParamMap.get('filter') || this.ALL;
    /*return this.activatedRoute.queryParamMap.pipe(
      map((params: ParamMap) => {
      if(params.get('filter') == null) {
        return "all";
      } else {
        return params.get('filter');
      }
    }),
    );*/
  }

  getPage(): number {
    return Number(this.activatedRoute.snapshot.queryParamMap.get('page') || "1");
    /*return this.activatedRoute.queryParamMap.pipe(
      map((params: ParamMap) => Number(params.get('page'))),
    );*/
  }

  getPageNum(): Observable<number> {
    return of(PAGE_NUM);
  }

  getSearch(): string {
    return this.activatedRoute.snapshot.queryParamMap.get('search') || "";
  }

  //checks whether string blank,null or undefined
  isBlank(str: string): boolean {
    return (!str || /^\s*$/.test(str));
  }
}
