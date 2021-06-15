import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductCatalogRoutingModule } from './product-catalog-routing.module';
import { ProductListComponent } from './product-list/product-list.component';
import { FilterComponent } from './filter/filter.component';
import { SearchComponent } from './search/search.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ProductComponent } from './product/product.component';
import {ProductCardComponent} from "./product-card/product-card.component";


@NgModule({
  declarations: [
    ProductListComponent,
    FilterComponent,
    SearchComponent,
    PaginationComponent,
    ProductComponent,
    ProductCardComponent
  ],
  imports: [
    CommonModule,
    ProductCatalogRoutingModule
  ]
})
export class ProductCatalogModule { }
