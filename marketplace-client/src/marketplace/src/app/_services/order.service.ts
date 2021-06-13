import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {CourierOrder} from "../_models/order-info/courier-order";
import {environment} from "../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderDto} from "../_models/order-info/order-dto";
import {catchError} from "rxjs/operators";
import {AuthService} from "../_auth/auth.service";

const baseUrl = `${environment.apiUrl}`; // Is it ok?

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private service: AuthService
  ) {}

  public getOrders(page: number): Observable<OrderDto> {
    let currentUrl = this.router.url.split('?')[0];

    this.router.navigate([currentUrl], {
      queryParams: {
        page: page
      }
    });

    return this.http.get<OrderDto>(baseUrl + '/orders', {
      params: new HttpParams().set('page', page.toString())
    })
      .pipe(catchError((error: any) => of(new OrderDto())));
  }

  public getOrder(): Observable<CourierOrder> {
    return this.http.get<CourierOrder>
    (baseUrl + '/orders/' + this.getOrderId());
  }

  /**
   * for couriers & for orders
   */

  private getOrderId(): string {
    return this.route.snapshot.params['orderId'];
  }

  private getCourierMail(): string {
    return this.route.snapshot.params['courierMail'];
  }

}
