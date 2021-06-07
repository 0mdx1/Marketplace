import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent {
  @Input() products: Product[] = [];
  /*constructor(
    private cdr: ChangeDetectorRef
  ) {}*/

  /*ngAfterViewInit() {
    this.cdr.detectChanges();
  }*/

  /*getStatusList() {
    return this.service.getStatusList();
  }

  createUser() {
    this.service.navigateToRegisterStaff();
  }*/
}
