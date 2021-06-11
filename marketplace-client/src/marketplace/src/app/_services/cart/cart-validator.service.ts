import { Injectable } from '@angular/core';
import {CartItem} from "../../_models/cart-item.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {CartItemCreateDto} from "../../_models/cart-item-create-dto.model";

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class CartValidatorService {

  constructor(private http: HttpClient) {}

  public validate(cart: CartItem[]):Observable<any> {
    let itemDtos: CartItemCreateDto[] = [];
    cart.forEach((item)=>{
      itemDtos.push(CartItemCreateDto.mapToDto(item));
    })
    return this.http.post<any>(`${baseUrl}/shopping-cart/validate/`,itemDtos);
  }
}
