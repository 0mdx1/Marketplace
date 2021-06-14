import {Component, Inject, OnInit} from '@angular/core';
import {Product} from "../../_models/products/product";
import {CartService} from "../../_services/cart/cart.service";
import {ProductComparisonService} from "../../_services/product-comparison/product-comparison";
import {LimitedProductComparisonService} from "../../_services/product-comparison/limited-product-comparison.service";

@Component({
  selector: 'app-product-comparison',
  templateUrl: './product-comparison.component.html',
  styleUrls: ['./product-comparison.component.css']
})
export class ProductComparisonComponent {

  public products: Product[] = [];

  constructor(
   @Inject(LimitedProductComparisonService)private comparisonService: ProductComparisonService,
    private cartService: CartService
  ) {
    this.products = this.comparisonService.getProducts();

  }

  removeProduct(product: Product){
    this.comparisonService.removeProduct(product);
  }

  addProductToCart(product: Product){
    this.cartService.addProduct(product)
  }
}
