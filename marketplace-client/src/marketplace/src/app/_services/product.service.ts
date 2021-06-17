import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { empty, Observable, of, pipe, Subject } from 'rxjs';
import { Filter } from '../_models/products/filter';
import { Product } from '../_models/products/product';
import { ProductDto } from '../_models/products/productDto';
import { catchError, switchMap } from 'rxjs/operators';

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  pageTotalSource: Subject<number> = new Subject();
  pageSource: Subject<number> = new Subject();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  private getProducts(
    filter: Filter,
    search: string,
    page: number
  ): Observable<ProductDto> {
    this.pageTotalSource.next()

    this.addQueryParams(filter, search, page);
    //get method to backend api
    return this.http
      .get<ProductDto>(`${baseUrl}/products`, {
        params: this.buildQueryParams(filter, search, page),
      })
      .pipe(catchError((error: any) => of(new ProductDto())));
  }

  getProduct(): Observable<Product> {
    return this.http.get<Product>(baseUrl + '/products/' + this.getProductId());
  }

  private getProductId(): string {
    let urlPart = this.router.url.split('/');
    return urlPart[urlPart.length - 1];
  }

  private buildQueryParams(
    filter: Filter,
    search: string,
    page: number
  ): HttpParams {
    let params = new HttpParams()
      .set('category', filter.category)
      .set('minPrice', filter.minPrice.toString())
      .set('maxPrice', filter.maxPrice.toString())
      .set('sort', filter.sort)
      .set('direction', filter.direction)
      .set('page', page.toString());
    if (!this.isBlank(search)) {
      //add search param only if it is not empty
      return params.set('name', search);
    }
    return params;
  }

  private addQueryParams
  (filter: Filter, search: string, page: number): string {
    //filter = this.validateFilter(filter);
    let currentUrl = this.router.url.split('?')[0];
    if (!this.isBlank(search)) {
      //add search param only if it is not empty
      this.router.navigate([currentUrl], {
        queryParams: {
          category: filter.category,
          minPrice: filter.minPrice,
          maxPrice: filter.maxPrice,
          sort: filter.sort,
          direction: filter.direction,
          page: page,
          search: search,
        }
      });
      currentUrl =
        currentUrl +
        '?category=' +
        filter.category +
        '&minPrice=' +
        filter.minPrice +
        '&maxPrice=' +
        filter.maxPrice +
        '&sort=' +
        filter.sort +
        '&direction=' +
        filter.direction +
        '&page=' +
        page +
        '&search=' +
        search;
    } else {
      this.router.navigate([currentUrl], {
        queryParams: {
          category: filter.category,
          minPrice: filter.minPrice,
          maxPrice: filter.maxPrice,
          sort: filter.sort,
          direction: filter.direction,
          page: page,
        },
      });
      currentUrl =
        currentUrl +
        '?category=' +
        filter.category +
        '&minPrice=' +
        filter.minPrice +
        '&maxPrice=' +
        filter.maxPrice +
        '&sort=' +
        filter.sort +
        '&direction=' +
        filter.direction +
        '&page=' +
        page;
    }
    return currentUrl;
  }

  getFilteredProducts(filter: Filter, init: boolean): Observable<ProductDto> {
    let products;
    if (init) {
      products = this.getProducts(
        filter,
        this.getSearch(),
        this.getCurrentPage()
      );
    } else {
      products = this.getProducts(filter, this.getSearch(), 1);
    }
    this.notifyPageComponent(products);
    return products;
  }

  getSearchedProducts(search: string, init: boolean): Observable<ProductDto> {
    let products;
    if (init) {
      products = this.getProducts(
        this.getFilter(),
        search,
        this.getCurrentPage()
      );
    } else {
      products = this.getProducts(this.getFilter(), search, 1);
    }
    this.notifyPageComponent(products);
    return products;
  }

  getPagedProducts(page: number): Observable<ProductDto> {
    return this.getProducts(this.getFilter(), this.getSearch(), page);
  }

  getCurrentPage(): number {
    return Number(this.activatedRoute.snapshot.queryParamMap.get('page') || 1);
  }

  getSearch() {
    return this.activatedRoute.snapshot.queryParamMap.get('search') || '';
  }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${baseUrl}/products/categories`);
  }

  getFilter(): Filter {
    return new Filter(
      this.activatedRoute.snapshot.queryParamMap.get('category') || 'all',
      Number(this.activatedRoute.snapshot.queryParamMap.get('minPrice') || 0),
      Number(
        this.activatedRoute.snapshot.queryParamMap.get('maxPrice') || 99999
      ),
      this.activatedRoute.snapshot.queryParamMap.get('sort') || 'name',
      this.activatedRoute.snapshot.queryParamMap.get('direction') || 'DESC'
    );
  }

  private notifyPageComponent(products: Observable<ProductDto>) {
    products.subscribe((res) => {
      this.pageTotalSource.next(res.total);
      this.pageSource.next(res.current);
    });
  }

  //checks whether string blank,null or undefined
  private isBlank(str: string): boolean {
    return !str || /^\s*$/.test(str);
  }

  AddProduct(account: Product): Observable<any> {
    return this.http.post(`${baseUrl}/products`, account);
  }

  getProductInfo(id: number){
    return this.http.get(`${baseUrl}/products/` + id);
  }

  updateProduct(account: Product, id: number): Observable<any> {
    return this.http.put(`${baseUrl}/products/` + id, account);
  }
}
