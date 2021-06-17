import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductComponent } from './product/product.component';
import {UpdateProductComponent} from "./update-product/update-product.component";
import {AddProductComponent} from "./add-product/add-product.component";
import {RoleGuardService} from "../_auth/auth.guard.role.service";
import {Role} from "../_models/role";
import {ProductComparisonComponent} from "./product-comparison/product-comparison.component";

const routes: Routes = [
  {
    path: 'add-product',
    component: AddProductComponent,
    canActivate: [RoleGuardService],
    data: { roles: [Role.ProductManager,Role.Admin] }
  },
  {
    path: 'comparison',
    component: ProductComparisonComponent,
    canActivate: [RoleGuardService],
    data: { roles: [Role.User,Role.AnonymousUser]}
  },
  { path: '', component: ProductListComponent },
  { path: ':id', component: ProductComponent },
  {
    path: 'update-product/:id',
    component: UpdateProductComponent,
    canActivate: [RoleGuardService],
    data: { roles: [Role.ProductManager,Role.Admin] }
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProductCatalogRoutingModule {}
