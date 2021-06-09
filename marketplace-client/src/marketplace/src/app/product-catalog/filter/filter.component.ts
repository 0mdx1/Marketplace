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
      .getFilteredProducts(filter, init)
      .subscribe((results: ProductDto) => {
        this.products = results.result_set;
        this.results.emit();
      });
  }

  chooseCategory(category: string) {
    console.log(category);
    this.filters.category = category;
    this.filter(this.filters, false);
  }

  private getFilter(): Filter {
    return this.service.getFilter();
  }

  private getCategories() {
    this.categorySubscription = this.service
      .getCategories()
      .subscribe((results: string[]) => {
        this.categories = results;
        this.categories.unshift('all');
      });
  }

  onSortChange(e: any) {
    console.log(e.target.value);
    if (e.target.value === 'nameAsc') {
      this.filters.sort = 'name';
      this.filters.direction = 'ASC';
    } else if (e.target.value === 'nameDesc') {
      this.filters.sort = 'name';
      this.filters.direction = 'DESC';
    } else if (e.target.value === 'priceAsc') {
      this.filters.sort = 'price';
      this.filters.direction = 'ASC';
    } else {
      this.filters.sort = 'price';
      this.filters.direction = 'DESC';
    }
    this.filter(this.filters, false);
  }

  getProducts(): Product[] {
    return this.products;
  }

  isPriceAsc(): boolean {
    return this.filters.sort === 'price' && this.filters.direction === 'ASC';
  }

  isPriceDesc(): boolean {
    return this.filters.sort === 'price' && this.filters.direction === 'DESC';
  }

  isNameAsc(): boolean {
    return this.filters.sort === 'name' && this.filters.direction === 'ASC';
  }

  isNameDesc(): boolean {
    return this.filters.sort === 'name' && this.filters.direction === 'DESC';
  }
}
