import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/_models/user';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import {OrderModel} from '../../_models/order/order.model';

const baseUrl = environment.apiUrl;

@Injectable({
  providedIn: 'root',
})
export class Checkout {
  constructor(private http: HttpClient) {}

  sendOrderDetails(data: OrderModel) {
    return this.http.post(`${baseUrl}/orders`, data);
  }

  getUser(): Observable<User> {
    return this.http.get<User>(`${baseUrl}/orders/userinfo`);
  }

  getDeliveryTime(): Observable<Date[]> {
    return this.http.get<Date[]>(`${baseUrl}/orders/freeslots`)
      .pipe(
        map((data: Date[]) => {
          return data.map((date: Date) => {
            return new Date(date);
          });
      })
    );
  }
}
