import {Inject, Injectable, OnDestroy} from '@angular/core';
import {PageCart} from "../cart/page-cart";
import {Cart} from "../cart/cart";
import {ProductComparisonService} from "./product-comparison";
import {PageProductComparisonService} from "./page-product-comparison.service";
import {CartItem} from "../../_models/cart-item.model";
import {Product} from "../../_models/products/product";

@Injectable({
  providedIn: 'root'
})
export class BrowserProductComparisonService implements ProductComparisonService, OnDestroy{

  constructor(@Inject(PageProductComparisonService)private comparisonService: ProductComparisonService) {
    this.comparisonService.setProducts(this.loadCartItems());
    window.addEventListener('storage',this.storageUpdateListener.bind(this));
  }

  private storageUpdateListener(): void{
    this.comparisonService.setProducts(this.loadCartItems());
  }

  ngOnDestroy(): void {
    window.removeEventListener('storage',this.storageUpdateListener.bind(this));
  }

  private loadCartItems() {
    return JSON.parse(localStorage.getItem('product-comparison') || null as any) || [];
  }

  private save(products: Product[]): void {
    localStorage.setItem('product-comparison', JSON.stringify(products));
  }

  addProduct(product: Product): void {
    this.comparisonService.addProduct(product);
    this.save(this.comparisonService.getProducts());
  }

  getProducts(): Product[] {
    return this.comparisonService.getProducts();
  }

  removeProduct(product: Product): void {
    this.comparisonService.removeProduct(product);
    this.save(this.comparisonService.getProducts());
  }

  setProducts(products: Product[]): void {
    this.comparisonService.setProducts(products);
    this.save(this.comparisonService.getProducts());
  }


}
