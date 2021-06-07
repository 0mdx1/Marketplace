import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { Filter } from 'src/app/_models/products/filter';
import { Product } from 'src/app/_models/products/product';
import { ProductDto } from 'src/app/_models/products/productDto';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  categories: string[] = [];
  filters!: Filter;

  products: Product[] = [];
  subscription!: Subscription;
  categorySubscription!: Subscription;
  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: ProductService) {}

  ngOnInit(): void {
    this.getCategories();
    this.filters = this.getFilter();
    this.filter(this.filters, true);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.categorySubscription.unsubscribe();
  }

  private filter(filter: Filter, init: boolean) {
    this.subscription = this.service
      .getFilteredProducts(this.filters, init)
      .subscribe((results: ProductDto) => {
        this.products = results.result_set;
        this.results.emit();
      });
  }

  /*onChange(e: any) {
    this.filterStatus(e.target.value, false);
  }*/

  private getFilter(): Filter {
    return this.service.getFilter();
  }

  private getCategories() {
    this.categorySubscription = this.service
      .getCategories()
      .subscribe((results: string[]) => {
        this.categories = results;
      });
  }

  getProducts(): Product[] {
    return this.products;
  }
}
