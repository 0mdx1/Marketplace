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

  product: Product = new Product(0, '', '', 0, 0, '', 0, false, false, '', '', '','');
  comparison: boolean = false;

  ngOnInit(): void {
    this.service.getProduct().subscribe((result: Product) => {
      this.product = result;
      this.comparison = this.inComparison();
    });
  }

  addToCart() {
    console.log(this.product);
    this.cartService.addProduct(this.product);
  }

  addToComparison() {
    this.comparison = true;
    this.comparisonService.addProduct(this.product);
  }

  inComparison():boolean {
    return this.comparisonService.getProducts().find(product => product.id==this.product.id)!=undefined;
  }
}
