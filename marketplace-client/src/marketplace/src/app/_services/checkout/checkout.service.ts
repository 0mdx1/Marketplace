import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CartItem } from "src/app/_models/cart-item.model";
import { User } from "src/app/_models/user";
import { environment } from "src/environments/environment";
import { map } from 'rxjs/operators';

const baseUrl = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class Checkout {

  constructor(private http: HttpClient) {}

  sendOrderDetails(data: any) {
    return this.http.post(`${baseUrl}/orders`, data);
  }

  getUser(): Observable<User> {
    return this.http.get<User>(`${baseUrl}/orders/userinfo`);
  }

  getDeliveryTime(): Observable<string[]> {
    return this.http.get(`${baseUrl}/orders/freeslots`).pipe(
      map((data: any) => {
        let time = data;
        return time.map(function(delivery: any): string {
          return delivery;
        })
      })
    );
  }
}