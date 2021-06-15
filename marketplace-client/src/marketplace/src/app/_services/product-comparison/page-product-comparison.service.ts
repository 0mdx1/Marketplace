import { Injectable } from '@angular/core';
import {Product} from "../../_models/products/product";
import {ProductComparisonService} from "./product-comparison";

@Injectable({
  providedIn: 'root'
})
export class PageProductComparisonService implements ProductComparisonService{
  private products: Product[] = []
  constructor() {}

  addProduct(product: Product): void {
    if(this.products.indexOf(product) == -1){
      this.products.push(product);
    }
  }

  setProducts(products: Product[]){
    this.products.splice(0);
    products.forEach((product)=>{
      this.addProduct(product);
    },this)
  }

  removeProduct(product: Product): void{
    this.products.splice(this.products.indexOf(product),1);
  }

  getProducts(): Product[]{
    return this.products;
  }
}
