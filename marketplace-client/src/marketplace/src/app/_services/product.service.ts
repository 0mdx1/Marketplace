import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ProductModelServer,
 // serverResponse
} from "../_models/product.model";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private url = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  getSingleProduct(id: number): Observable<ProductModelServer> {
    return this.http.get<ProductModelServer>(this.url + 'products/' + id);
  }
  // getAllProducts(limitOfResults=10): Observable<serverResponse> {
  //   return this.http.get<serverResponse>(this.url + 'products', {
  //     params: {
  //       limit: limitOfResults.toString()
  //     }
  //   });
  // }



  // getProductsFromCategory(catName: String): Observable<ProductModelServer[]> {
  //   return this.http.get<ProductModelServer[]>(this.url + 'products/category/' + catName);
  // }

}
