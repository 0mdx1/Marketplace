import { Component, Input} from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { ProductService } from 'src/app/_services/product.service';
import {CartService} from "../../_services/cart/cart.service";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent {

  @Input() product: Product = new Product(0, '', '', 0, 0, '', 0, false, false, '', '', '', '');
  @Input() role: string | null = null;

  constructor(private cartService: CartService) {
  }

  addToCart() {
    if(this.product)this.cartService.addProduct(this.product);
  }

}
