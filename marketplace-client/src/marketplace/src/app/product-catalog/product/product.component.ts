import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { CartService } from 'src/app/_services/cart/cart.service';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  constructor(
    private service: ProductService,
    private cartService: CartService
  ) {}
  product: Product = new Product(0, '', '', 0, 0, '', 0, false, '', '');

  ngOnInit(): void {
    this.service.getProduct().subscribe((result: Product) => {
      this.product = result;
    });
  }

  addToCart() {
    this.cartService.addProduct(this.product);
  }
}
