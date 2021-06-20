import { Component, Input } from '@angular/core';
import { AlertType } from 'src/app/_models/alert';
import { Product } from 'src/app/_models/products/product';
import { AlertService } from 'src/app/_services/alert.service';
import { ProductService } from 'src/app/_services/product.service';
import { CartService } from '../../_services/cart/cart.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent {
  @Input() product: Product = new Product(
    0,
    '',
    '',
    0,
    0,
    '',
    0,
    false,
    false,
    '',
    '',
    '',
    ''
  );
  @Input() role: string | null = null;

  constructor(
    private cartService: CartService,
    private alertService: AlertService
  ) {}

  addToCart() {
    if (this.product) {
      this.cartService.addProduct(this.product);
      this.alertService.addAlert('Added to cart', AlertType.Success);
    }
  }
}
