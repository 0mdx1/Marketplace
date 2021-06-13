import { Injectable } from '@angular/core';
import {Product} from "../../_models/products/product";

export interface ProductComparisonService {

  addProduct(product: Product): void

  setProducts(products: Product[]): void

  removeProduct(product: Product): void

  getProducts(): Product[]
}
