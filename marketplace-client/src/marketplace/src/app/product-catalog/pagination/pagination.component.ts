import {
  Component, EventEmitter, OnDestroy,
  OnInit, Optional,
  Output,
} from '@angular/core';
import {Product} from 'src/app/_models/products/product';
import {ProductDto} from 'src/app/_models/products/productDto';
import {ProductService} from 'src/app/_services/product.service';
import {of, Subject} from "rxjs";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent implements OnInit {
  currentPage: number = 1;
  pageNum: number = 1; //total number of pages
  products: Product[] = [];

  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: ProductService) {}

  ngOnInit(): void {

    // const subject$ = new Subject();
    //
    // subject$.subscribe({
    //     next(value) {
    //       console.log(value)
    //     }
    //   }
    // )
    // subject$.next('Hello World')
    // of(1, 4, 6).subscribe()

    this.service.pageTotalSource.subscribe((pageNum) => {
      this.pageNum = pageNum;
    });
    //TODO: get page twice?
    this.service.pageSource.subscribe((page) => {
      this.currentPage = page;
    });
    this.currentPage = this.service.getCurrentPage();

    this.service
      .getPagedProducts(this.currentPage)
      .subscribe((results: ProductDto) => {
        this.products = results.result_set;
        this.results.emit();
        this.pageNum = results.total;
        this.currentPage = results.current;
      });

    if (Number.isNaN(this.currentPage) || this.currentPage < 1) {
      this.currentPage = 1;
    }
    if (this.currentPage > this.pageNum) {
      this.currentPage = this.pageNum;
    }
  }

  nextPage(): void {
    this.currentPage = this.currentPage + 1;
    this.getPage();
  }

  prevPage(): void {
    this.currentPage = this.currentPage - 1;
    this.getPage();
  }

  private getPage(): void {
    this.service
      .getPagedProducts(this.currentPage)
      .subscribe((results: ProductDto) => {
        this.products = results.result_set;
        this.results.emit();
        this.pageNum = results.total;
        this.currentPage = results.current;
      });
  }

  getProducts(): Product[] {
    return this.products;
  }


}
