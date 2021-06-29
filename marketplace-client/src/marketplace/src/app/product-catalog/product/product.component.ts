import { Component, Inject, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { CartService } from 'src/app/_services/cart/cart.service';
import { ProductService } from 'src/app/_services/product.service';
import { LimitedProductComparisonService } from '../../_services/product-comparison/limited-product-comparison.service';
import { ProductComparisonService } from '../../_services/product-comparison/product-comparison';
import { AuthService } from '../../_auth/auth.service';
import { AlertService } from 'src/app/_services/alert.service';
import { AlertType } from 'src/app/_models/alert';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  constructor(
    private service: ProductService,
    private authService: AuthService,
    private cartService: CartService,
    @Inject(LimitedProductComparisonService)
    private comparisonService: ProductComparisonService,
    private alertService: AlertService
  ) {}

  product: Product = new Product(
    -1,
    '',
    '',
    0,
    0,
    '',
    0,
    false,
    false,
    new Date(0),
    '',
    '',
    ''
  );
  comparison: boolean = false;
  role: string | null = 'ROLE_USER';

  ngOnInit(): void {
    this.service.getProduct().subscribe((result: Product) => {
      this.product = result;
      this.comparison = this.inComparison();
      this.role = this.authService.getRole();
    });
  }

  addToCart() {
    this.cartService.addProduct(this.product);
    this.alertService.addAlert('Added to cart', AlertType.Success);
  }

  addToComparison() {
    this.comparison = true;
    this.comparisonService.addProduct(this.product);
  }

  inComparison(): boolean {
    return (
      this.comparisonService
        .getProducts()
        .find((product) => product.id == this.product.id) != undefined
    );
  }
}
