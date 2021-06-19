import {Component, OnInit} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import {ProductService} from '../../_services/product.service';
import {first} from 'rxjs/operators';
import {Role} from '../../_models/role';
import {Product} from '../../_models/products/product';
import {AccountService} from "../../_services/account.service";

@Component({
  selector: 'app-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],

})
export class AddProductComponent implements OnInit {
  form: FormGroup;

  submitted = false;

  roles: Role[] = [Role.Courier, Role.ProductManager];

  unit: string[] = ["KILOGRAM", "ITEM", "LITRE"];
  inStockStatus: string[] = ["true", "false"];
  categoryName: string[] = ["fruits", "vegetables", "meat", "drinks", "water"];
  status: string[] = ["true", "false"];
  firmName: string[] = [""];

  loading = false;
  registered = false;
  image: string = '';

  responseCategory: any;
  responseFirm: any;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: ProductService,
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
        status: ['', Validators.required],
        categoryName: ['', Validators.required],
        description: ['', Validators.required],
      },
    );
  }

  ngOnInit() {
    this.firm();
    this.category()
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  public setImage(imageName: string) {
    this.image = imageName;
  }

  public category() {
    this.accountService.getCategories()
      .subscribe((categ) => {
        this.responseCategory = categ;
        console.log(this.responseCategory);
        this.categoryName = this.responseCategory;
      })
  }

  public firm() {
    this.accountService.getFirm()
      .subscribe((firm) => {
        this.responseFirm = firm;
        console.log(this.responseFirm);
        this.firmName = this.responseFirm;
      })
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
      status: o.status,
      categoryName: o.categoryName,
      description: o.description,
    };
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      console.log(this.form.value);
      console.log("dont work");
      return;
    }
    this.loading = true;

    let observable = null;
    let product = this.mapToProduct(this.form.value);
    product.image = this.image;
    observable = this.accountService.AddProduct(
      product
    );

    observable.pipe(first()).subscribe({

      next: () => {
        this.loading = false;
        this.registered = true;
      }
    });
  }
}
