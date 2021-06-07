import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  constructor(private service: ProductService) {}
  product!: Product;

  ngOnInit(): void {
    this.service
      .getProduct()
      .subscribe((result: Product) => (this.product = result));
  }
}
