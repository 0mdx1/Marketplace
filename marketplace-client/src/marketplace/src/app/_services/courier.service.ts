import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { COURIERS } from '../_helpers/mock-couriers';
import { Courier } from '../_models/courier';

@Injectable({
  providedIn: 'root'
})
export class CourierService {

  constructor() { }

  getCouriers(): Observable<Courier[]> {
    return of(COURIERS);
  }
}
