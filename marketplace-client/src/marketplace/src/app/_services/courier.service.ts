import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { COURIERS } from '../_helpers/mock-couriers';
import { Courier } from '../_models/courier';

@Injectable({
  providedIn: 'root'
})
export class CourierService {

  params: HttpParams;
  filterBy: string = "";

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router) {
    this.params = new HttpParams();
  }

  getCouriers(): Observable<Courier[]> {
    return of(COURIERS);
  }

  filterCouriers(filter: string): void {
    this.params = this.params.set('filter', filter);
    /*this.activatedRoute.queryParams.subscribe(params => {
      this.filterBy= params['filter'];
      console.log(this.filterBy);
  });*/
  }
}
