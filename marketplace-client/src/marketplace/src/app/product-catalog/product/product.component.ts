import {Component, Inject, OnInit} from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { CartService } from 'src/app/_services/cart/cart.service';
import { ProductService } from 'src/app/_services/product.service';
import {LimitedProductComparisonService} from "../../_services/product-comparison/limited-product-comparison.service";
import {ProductComparisonService} from "../../_services/product-comparison/product-comparison";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  constructor(
    private service: ProductService,
    private cartService: CartService,
    @Inject(LimitedProductComparisonService)
    private comparisonService: ProductComparisonService,
  ) {}


  product: Product = new Product(0, '', '', 0, 0, '', 0, false, '', '', '');

  ngOnInit(): void {
    this.service.getProduct().subscribe((response: Product) => {
      this.product = response;
    });
  }

  addToCart() {
    console.log(this.product);
    this.cartService.addProduct(this.product);
  }
  addToComparison() {
    this.comparisonService.addProduct(this.product);
  }
}
