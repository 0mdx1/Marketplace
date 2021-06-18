import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent {
  @Input() products: Product[] = [];
}
