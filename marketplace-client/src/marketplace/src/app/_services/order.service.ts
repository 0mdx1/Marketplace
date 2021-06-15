import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of, Subject} from "rxjs";
import {CourierOrder} from "../_models/order-info/courier-order";
import {environment} from "../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderPage} from "../_models/order-info/order-page";
import {Status} from "../_models/status";

const baseUrl = `${environment.apiUrl}`; // Is it ok?

@Injectable({
  providedIn: 'root'
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
        page: page
      }
    });

    return this.http.get<OrderPage>(baseUrl + '/orders', {
      params: new HttpParams().set('page', page.toString())
    });

  // .pipe(catchError((error: any) => of(new OrderPage())))
  }

  public getOrder(): Observable<CourierOrder> {
    return this.http.get<CourierOrder>
    (baseUrl + '/orders/' + this.getOrderId());
  }

  private getOrderId(): string {
    return this.activatedRoute.snapshot.params['orderId'];
  }

  public changeStatus(): Observable<Status> {
    return this.http.get<Status>(baseUrl + '/orders/' + this.getOrderId() + '/status')
  }

}
