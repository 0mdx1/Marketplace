import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductCatalogRoutingModule } from './product-catalog-routing.module';
import { ProductListComponent } from './product-list/product-list.component';
import { FilterComponent } from './filter/filter.component';
import { SearchComponent } from './search/search.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ProductComponent } from './product/product.component';
import {ProductCardComponent} from "./product-card/product-card.component";
import {NgxSliderModule} from "@angular-slider/ngx-slider";
import {FormsModule} from "@angular/forms";
import {AddProductComponent} from "./add-product/add-product.component";
import {UpdateProductComponent} from "./update-product/update-product.component";
import {ReactiveFormsModule} from "@angular/forms";
import {AppModule} from "../app.module";
import {FileUploadingModule} from "../file-uploading/file-uploading.module";


@NgModule({
  declarations: [
    ProductListComponent,
    FilterComponent,
    SearchComponent,
    PaginationComponent,
    ProductComponent,
    ProductCardComponent,
    AddProductComponent,
    UpdateProductComponent,
  ],
    imports: [
        CommonModule,
        ProductCatalogRoutingModule,
        NgxSliderModule,
        FormsModule,
        ReactiveFormsModule,
        ProductCatalogRoutingModule,
        FileUploadingModule
    ]
})
export class ProductCatalogModule { }
