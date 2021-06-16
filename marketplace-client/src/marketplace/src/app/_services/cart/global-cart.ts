import {Inject, Injectable, OnDestroy} from "@angular/core";
import {interval, Observable, of, Subscription} from "rxjs";

import {CartItem} from "../../_models/cart-item.model";
import {Cart} from "./cart";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../_auth/auth.service";
import {CartItemCreateDto} from "../../_models/cart-item-create-dto.model";
import {BrowserCart} from "./browser-cart";
import {environment} from "../../../environments/environment";
import {switchMap} from "rxjs/operators";
import {Role} from "../../_models/role";

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class GlobalCart implements Cart, OnDestroy{

  private subscription: Subscription | null = null;

  private initialized: boolean = false;

  constructor(
    @Inject(BrowserCart)private cart: Cart,
    private http: HttpClient,
    private auth: AuthService
  ) {
    if(this.alowedToSendRequests()) {
      this.init().subscribe(() => {});
    }
    this.subscription = interval(10000)
      .subscribe(() => {
        if(this.alowedToSendRequests()) {
          this.init().subscribe(() => {
            if(!document.hidden){
              console.log("polling...");
              this.getShoppingCart()
                .subscribe({
                  next: items => {
                    if (JSON.stringify(items) !== JSON.stringify(this.cart.getItems())) {
                      console.log("changes detected. updating...")
                      this.cart.setItems(items);
                    }
                  },
                  error: e => console.log(e)
                })
            }
          })
        }
    });
  }

  private getShoppingCart() {
    return this.http.get<CartItem[]>(`${baseUrl}/shopping-cart/`);
  }

  ngOnDestroy(): void {
    if(this.subscription!==null) {
      this.subscription.unsubscribe();
    }
  }

  private init(): Observable<any>{
    if(!this.initialized){
      this.initialized = true;
      if(this.cart.getItems().length>0){
        console.log("initializing with items from browser cart");
        return this.putShoppingCart(this.cart.getItems());
      }
      console.log("initializing with items from cloud cart");
      return this.getShoppingCart()
        .pipe(switchMap(items => {
          this.cart.setItems(items);
          return of({});
        }))
    }
    return of({});
  }

  private mapToDto(item: CartItem): CartItemCreateDto {
    return new CartItemCreateDto(item.goods.id,item.addingTime,item.quantity);
  }

  addItem(item: CartItem): void {
    this.cart.addItem(item);
    if(this.alowedToSendRequests()){
      this.init().subscribe(()=>{
        this.putShoppingCartItem(item)
          .subscribe({error: e=>console.log(e)});
      })
    }
  }

  empty(): void {
    this.cart.empty();
    if(this.alowedToSendRequests()) {
      this.init().subscribe(()=>{
        this.deleteShoppingCart()
          .subscribe({error: e => console.log(e)});
      })
    }
  }

  private alowedToSendRequests() {
    return this.auth.isAuthenticated()&&this.auth.isExpectedRole(Role.User);
  }

  getItems(): CartItem[] {
    return this.cart.getItems();
  }

  removeItem(item: CartItem): void {
    this.cart.removeItem(item);
    if(this.alowedToSendRequests()) {
      this.init().subscribe(()=>{
        this.deleteShoppingCartItem(item)
          .subscribe({error: e => console.log(e)});
      })
    }
  }



  setItems(items: CartItem[]): void {
    this.cart.setItems(items);
    if(this.alowedToSendRequests()) {
      this.init().subscribe(()=>{
        this.putShoppingCart(items)
          .subscribe({
            complete: ()=>(console.log("completed")),
            error: e=>console.log(e)
          });
      })
    }
  }

  updateItem(item: CartItem): void {
    this.cart.updateItem(item);
    if(this.alowedToSendRequests()) {
      this.init().subscribe(()=>{
        this.patchShoppingCartItem(item)
          .subscribe({error: e => console.log(e)});
      })
    }
  }

  getItem(productId: number): CartItem | null {
    return this.cart.getItem(productId);
  }

  private deleteShoppingCartItem(item: CartItem): Observable<any>{
    return this.http
      .delete(`${baseUrl}/shopping-cart/item/${item.goods.id}/`);
  }

  private deleteShoppingCart(): Observable<any>{
    return this.http.delete(`${baseUrl}/shopping-cart/`);
  }

  private putShoppingCartItem(item: CartItem): Observable<any>{
    return this.http.put(`${baseUrl}/shopping-cart/item/`,this.mapToDto(item));
  }

  private patchShoppingCartItem(item: CartItem): Observable<any>{
    return this.http
      .patch(`${baseUrl}/shopping-cart/item/${item.goods.id}/`, this.mapToDto(item))
  }

  private putShoppingCart(items: CartItem[]): Observable<any>{
    let itemDtos: CartItemCreateDto[] = [];
    items.forEach((item)=>{
      itemDtos.push(this.mapToDto(item));
    })
    return this.http.put(`${baseUrl}/shopping-cart/`,itemDtos);
  }
}
