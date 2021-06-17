import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../_services/product.service';
import { validateBirthday } from '../../_helpers/validators.service';
import { first } from 'rxjs/operators';
import { Role } from '../../_models/role';
import { StaffMember } from '../../_models/staff-member';
import { Product } from '../../_models/products/product';
import {AccountService} from "../../_services/account.service";

@Component({
  selector: 'app-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],

})
export class AddProductComponent {
  form: FormGroup;

  submitted = false;

  roles: Role[] = [Role.Courier, Role.ProductManager];

  unit: string[] = ["KILOGRAM", "ITEM", "LITRE"];
  inStockStatus: string[] = ["true", "false", "null"];
  categoryName: string[]= ["fruits", "vegetables", "meat", "drinks", "water"];

  loading = false;
  registered = false;
  image: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
     private productService: ProductService,
  ) {
  this.form = this.formBuilder.group(
      {
        goodName: ['', Validators.required],
        firmName: ['', Validators.required],
        quantity: ['', [Validators.min(1), Validators.required]],
        price: ['', [Validators.min(1), Validators.required]],
        unit: ['', Validators.required],
        discount: ['', [Validators.min(1), Validators.required]],
        inStock: ['', Validators.required],
        categoryName: ['', Validators.required],
        description: ['', Validators.required],
      },
    );
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  public setImage(imageName: string){
    this.image = imageName;
  }


  private mapToProduct(o: any): Product {
    return {
      id: -1,
      goodName: o.goodName,
      firmName: o.firmName,
      quantity: o.quantity,
      price: o.price,
      unit: o.unit,
      image: o.image,
      discount: o.discount,
      inStock: o.inStock,
      categoryName: o.categoryName,
      description: o.description,
    };
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;

    let observable = null;
    let product = this.mapToProduct(this.form.value);
    product.image = this.image;
    observable = this.productService.AddProduct(
      product
    );

    observable.pipe(first()).subscribe({

            next: () => {
              console.log("Role mistake");
        this.loading = false;
        this.registered = true;
      }
    });
  }
}
