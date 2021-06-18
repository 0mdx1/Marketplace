import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Subscription } from 'rxjs';
import { Filter } from 'src/app/_models/products/filter';
import { Product } from 'src/app/_models/products/product';
import { ProductDto } from 'src/app/_models/products/productDto';
import { ProductService } from 'src/app/_services/product.service';
import { Options } from '@angular-slider/ngx-slider';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss'],
})
export class FilterComponent implements OnInit {
  categories: string[] = [];
  filters!: Filter;

  products: Product[] = [];
  subscription!: Subscription;
  categorySubscription!: Subscription;
  minPrice: number = 0;
  maxPrice: number = 99999;
  options: Options = {
    floor: 0,
    ceil: 400,
    showTicks: false,
  };
  @Input() sort: string = "name";
  @Input() direction: string = "ASC";
  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: ProductService) {}

  ngOnInit(): void {
    this.getCategories();
    this.filters = this.getFilter();
    this.filters.sort=this.sort;
    this.filters.direction=this.direction;
    this.filters.maxPrice=this.maxPrice;
    this.filters.minPrice=this.minPrice;
    this.filter(this.filters, true);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.categorySubscription.unsubscribe();
  }

  ngOnChanges():void{
    if(this.filters) {
      this.filters.sort = this.sort;
      this.filters.direction = this.direction;
      this.filter(this.filters, false);
    }
  }

  private filter(filter: Filter, init: boolean, category?: boolean) {
    this.subscription = this.service
      .getFilteredProducts(filter, init)
      .subscribe((results: ProductDto) => {
        this.products = results.result_set;
        if(category || init){
          const maxPrice = this.getMaxPrice();
          this.options= {
            floor: 0,
            ceil: maxPrice,
            showTicks: false,
          };
          this.minPrice = 0;
          this.maxPrice = maxPrice;
        }
        this.results.emit();
      });
  }

  chooseCategory(category: string) {
    console.log(category);
    this.filters.category = category;
    this.filters.maxPrice = 99999;
    this.filters.minPrice = 0;
    this.filter(this.filters, false, true);
  }

  setPrice():void {
    this.filters.maxPrice=this.maxPrice;
    this.filters.minPrice=this.minPrice;
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

  getMaxPrice(): number {
    return Math.max.apply(Math, this.products.map(function(product) { return product.price; }));
  }
}
