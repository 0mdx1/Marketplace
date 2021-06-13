import {Inject, Injectable} from '@angular/core';
import {ProductComparisonService} from "./product-comparison";
import {PageProductComparisonService} from "./page-product-comparison.service";
import {Product} from "../../_models/products/product";
import {AlertService} from "../alert.service";
import {AlertType} from "../../_models/alert";
import {BrowserProductComparisonService} from "./browser-product-comparison.service";

const maxProducts = 4;

@Injectable({
  providedIn: 'root'
})
export class LimitedProductComparisonService implements ProductComparisonService{

  constructor(
    @Inject(BrowserProductComparisonService)private comparisonService: ProductComparisonService,
    private alertService: AlertService
  ) {}

  addProduct(product: Product): void {
    if(this.comparisonService.getProducts().length < maxProducts){
      this.comparisonService.addProduct(product);
      return;
    }
    this.alertService.addAlert(`Cannot add more than ${maxProducts} to comparison`,AlertType.Danger);
  }

  getProducts(): Product[] {
    return this.comparisonService.getProducts();
  }

  removeProduct(product: Product): void {
    this.comparisonService.removeProduct(product);
  }

  setProducts(products: Product[]): void {
    if(products.length < maxProducts){
      this.comparisonService.setProducts(products);
      return;
    }
    this.alertService.addAlert(`Cannot add more than ${maxProducts} to comparison`,AlertType.Danger);
  }
}
