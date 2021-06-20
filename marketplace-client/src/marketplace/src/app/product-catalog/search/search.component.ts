import {
  Component,
  ElementRef,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { fromEvent, merge, Subscription } from 'rxjs';
import { debounceTime, map, switchAll } from 'rxjs/operators';
import { Product } from 'src/app/_models/products/product';
import { ProductDto } from 'src/app/_models/products/productDto';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit, OnDestroy {
  @Output() results: EventEmitter<any> = new EventEmitter<any>();
  products: Product[] = [];
  search: string = '';
  init: boolean = true;
  subscription!: Subscription;

  constructor(private service: ProductService, private el: ElementRef) {}

  ngOnInit(): void {
    this.search = this.service.getSearch();

    this.subscription = merge(
      fromEvent(this.el.nativeElement, 'keyup'),
      fromEvent(this.el.nativeElement, 'search')
    )
      .pipe(
        map((e: any) => e.target.value),
        debounceTime(250),
        map((query: string) => {
          const obs = this.service.getSearchedProducts(query, this.init);
          this.init = false;
          return obs;
        }),
        switchAll()
      )
      .subscribe(
        (results: ProductDto) => {
          this.products = results.result_set;
          this.results.emit();
        },
        (error) => {
          this.products = [];
          this.results.emit();
        }
      );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getProducts(): Product[] {
    return this.products;
  }
}
