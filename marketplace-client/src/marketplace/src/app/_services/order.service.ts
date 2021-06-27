import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CourierOrder } from '../_models/order-info/courier-order';
import { environment } from '../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderPage } from '../_models/order-info/order-page';
import { Status } from '../_models/status';
import { User } from '../_models/user';
import { map } from 'rxjs/operators';

const baseUrl = `${environment.apiUrl}`; // Is it ok?

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  public getPagedOrders(page: number): Observable<OrderPage> {
    let currentUrl = this.router.url.split('?')[0];

    this.router.navigate([currentUrl], {
      queryParams: {
        page: page,
      },
    });

    return this.http
      .get<OrderPage>(baseUrl + '/orders', {
        params: new HttpParams().set('page', page.toString()),
      })
      .pipe(
        map((orderPage: OrderPage) => {
          orderPage.orders = orderPage.orders.map((order: CourierOrder) => {
            order.deliveryTime = new Date(order.deliveryTime);
            return order;
          });
          return orderPage;
        })
      );
  }

  public getOrder(): Observable<CourierOrder> {
    return this.http
      .get<CourierOrder>(baseUrl + '/orders/' + this.getOrderId())
      .pipe(
        map((order: CourierOrder) => {
          order.deliveryTime = new Date(order.deliveryTime);
          return order;
        })
      );
  }

  private getOrderId(): string | null {
    // return this.activatedRoute.snapshot.queryParams.id;
    let arr = this.router.url.split('/');
    return arr[arr.length - 1];
  }

  public changeStatus(): Observable<Status> {
    return this.http.post<Status>(
      baseUrl + '/orders/' + this.getOrderId() + '/status',
      null
    );
  }

  //User order history
  public getOrdersForUser(): Observable<CourierOrder[]> {
    if (this.router.url.includes('previous')) {
      return this.http.get<CourierOrder[]>(`${baseUrl}/orders/history`).pipe(
        map((orders: CourierOrder[]) => {
          return orders.map((order: CourierOrder) => {
            order.deliveryTime = new Date(order.deliveryTime);
            return order;
          });
        })
      );
    }
    return this.http.get<CourierOrder[]>(`${baseUrl}/orders/incoming`).pipe(
      map((orders: CourierOrder[]) => {
        return orders.map((order: CourierOrder) => {
          order.deliveryTime = new Date(order.deliveryTime);
          return order;
        });
      })
    );
  }

  public getCourier(): Observable<User> {
    return this.http.get<User>(`${baseUrl}/orders/userinfo`);
  }

  public getUserOrder(): Observable<CourierOrder> {
    let pathParts = this.router.url.split('/');
    return this.http
      .get<CourierOrder>(baseUrl + '/orders/' + pathParts[pathParts.length - 1])
      .pipe(
        map((order: CourierOrder) => {
          order.deliveryTime = new Date(order.deliveryTime);
          return order;
        })
      );
  }

  public getCourierInfo(): Observable<User> {
    let pathParts = this.router.url.split('/');
    return this.http.get<User>(
      baseUrl + '/orders/' + pathParts[pathParts.length - 1] + '/courierinfo'
    );
  }
}
