import {Inject, Injectable, OnDestroy, OnInit} from "@angular/core";
import {interval, Observable, Subscription, forkJoin, of} from "rxjs";

import {CartItem} from "../../_models/cart-item.model";
import {Cart} from "./cart";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../_auth/auth.service";
import {CartItemCreateDto} from "../../_models/cart-item-create-dto.model";
import {BrowserCart} from "./browser-cart";
import {environment} from "../../../environments/environment";
import {switchMap} from "rxjs/operators";

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
      this.subscription = interval(10000)
        .subscribe(() => {
          if(this.auth.isAuthenticated()) {
            this.init().subscribe(() => {
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
    if(this.auth.isAuthenticated()){
      this.init().subscribe(()=>{
        this.putShoppingCartItem(item)
          .subscribe({error: e=>console.log(e)});
      })
    }
  }

  empty(): void {
    this.cart.empty();
    if(this.auth.isAuthenticated()) {
      this.init().subscribe(()=>{
        this.deleteShoppingCart()
          .subscribe({error: e => console.log(e)});
      })
    }
  }

  getItems(): CartItem[] {
    return this.cart.getItems();
  }

  removeItem(item: CartItem): void {
    this.cart.removeItem(item);
    if(this.auth.isAuthenticated()) {
      this.init().subscribe(()=>{
        this.deleteShoppingCartItem(item)
          .subscribe({error: e => console.log(e)});
      })
    }
  }



  setItems(items: CartItem[]): void {
    this.cart.setItems(items);
    if(this.auth.isAuthenticated()) {
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
    if(this.auth.isAuthenticated()) {
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
    return this.http.put(`${baseUrl}/shopping-cart/`,this.mapToDto(item));
  }

  private patchShoppingCartItem(item: CartItem): Observable<any>{
    return this.http
      .patch(`${baseUrl}/shopping-cart/item/${item.goods.id}/`, this.mapToDto(item))
  }

  private putShoppingCart(items: CartItem[]): Observable<any>{
    return this.deleteShoppingCart().pipe(switchMap((v)=>{
      let requests: Observable<any>[] = []
      items.forEach(
        item => {
          requests.push(this.putShoppingCartItem(item))
        });
      return forkJoin(requests)
    }));
  }
}
