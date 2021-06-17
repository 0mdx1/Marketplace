// import {Component, EventEmitter, OnInit, Output} from '@angular/core';
// import {Product} from "../_models/products/product";
// import {ProductService} from "../_services/product.service";
// import {ProductDto} from "../_models/products/productDto";
//
// @Component({
//   selector: 'app-pagination',
//   templateUrl: './pagination.component.html',
//   styleUrls: ['./pagination.component.css']
// })
// export class PaginationComponent implements OnInit {
//
//   currentPage: number = 1;
//   pageNum: number = 1; //total number of pages
//   objects?: any[];
//   @Output() results: EventEmitter<any> = new EventEmitter<any>();
//
//   constructor(private service: any) {}
//
//   ngOnInit(): void {
//     this.service.pageNumSource.subscribe((pageNum) => {
//       this.pageNum = pageNum;
//     });
//     this.service.pageSource.subscribe((page) => {
//       this.currentPage = page;
//     });
//     this.currentPage = this.service.getCurrentPage();
//
//     this.service
//       .getPagedProducts(this.currentPage)
//       .subscribe((results: ProductDto) => {
//         //this.results.emit(results.users);
//         this.products = results.result_set;
//         this.results.emit();
//         this.pageNum = results.total;
//         this.currentPage = results.current;
//       });
//
//     if (Number.isNaN(this.currentPage) || this.currentPage < 1) {
//       this.currentPage = 1;
//     }
//     if (this.currentPage > this.pageNum) {
//       this.currentPage = this.pageNum;
//     }
//   }
//
//   nextPage(): void {
//     this.currentPage = this.currentPage + 1;
//     this.getPage();
//   }
//
//   prevPage(): void {
//     this.currentPage = this.currentPage - 1;
//     this.getPage();
//   }
//
//   private getPage(): void {
//     this.service
//       .getPagedProducts(this.currentPage)
//       .subscribe((results: ProductDto) => {
//         //this.results.emit(results.users);
//         this.products = results.result_set;
//         this.results.emit();
//         this.pageNum = results.total;
//         this.currentPage = results.current;
//       });
//   }
//
//   getProducts(): Product[] {
//     return this.products;
//   }
// }
