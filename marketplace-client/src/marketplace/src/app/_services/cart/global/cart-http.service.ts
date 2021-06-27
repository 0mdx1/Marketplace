import {Injectable, OnDestroy} from '@angular/core';
import {CartItem} from "../../../_models/cart-item.model";
import {Observable, Subscription} from "rxjs";
import {CartItemCreateDto} from "../../../_models/cart-item-create-dto.model";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class CartHttpService{

  constructor(private http: HttpClient) { }

  public deleteShoppingCartItem(item: CartItem): Observable<any>{
    return this.http
      .delete(`${baseUrl}/shopping-cart/item/${item.goods.id}/`);
  }

  public deleteShoppingCart(): Observable<any>{
    return this.http.delete(`${baseUrl}/shopping-cart/`);
  }

  public putShoppingCartItem(item: CartItem): Observable<any>{
    return this.http.put(`${baseUrl}/shopping-cart/item/`,CartItemCreateDto.mapToDto(item));
  }

  public patchShoppingCartItem(item: CartItem): Observable<any>{
    return this.http
      .patch(`${baseUrl}/shopping-cart/item/${item.goods.id}/`, CartItemCreateDto.mapToDto(item))
  }

  public putShoppingCart(items: CartItem[]): Observable<any>{
    let itemDtos: CartItemCreateDto[] = [];
    items.forEach((item)=>{
      itemDtos.push(CartItemCreateDto.mapToDto(item));
    })
    return this.http.put(`${baseUrl}/shopping-cart/`,itemDtos);
  }

  public getShoppingCart(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${baseUrl}/shopping-cart/`);
  }
}
