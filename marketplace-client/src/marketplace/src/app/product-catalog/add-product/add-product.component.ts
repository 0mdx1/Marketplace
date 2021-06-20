import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators,} from '@angular/forms';
import {ProductService} from '../../_services/product.service';
import {first} from 'rxjs/operators';
import {Product} from '../../_models/products/product';
import {AccountService} from "../../_services/account.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {AlertService} from "../../_services/alert.service";
import {AlertType} from "../../_models/alert";

@Component({
  selector: 'app-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],

})
export class AddProductComponent implements OnInit{
  form: FormGroup;

  subscriptions: Subscription = new Subscription();

  d = Date().toLocaleString();



  submitted = false;
  unit: string[] = ["KILOGRAM", "ITEM", "LITRE"];
  status: string[] = ["true", "false"];
  firmName: string[]=[""];
  categoryName: string[]= [""];

  goodName: string = "New product";

  loading = false;
  registered = false;
  image: string = '';

  responseCategory: any;
  responseFirm: any;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private productService: ProductService,
    private router: Router,
    private alertService: AlertService
  ) {
    this.form = this.formBuilder.group(
      {
        goodName: ['', Validators.required],
        firmName: ['', Validators.required],
        quantity: ['', [Validators.min(1), Validators.required]],
        price: ['', [Validators.min(1), Validators.required]],
        unit: ['', Validators.required],
        discount: ['', Validators.min(0)],
        inStock: ['', Validators.required],
        status: ['', Validators.required],
        shippingDate: ['', Validators.required],
        categoryName: ['', Validators.required],
        description: ['', Validators.required],
      },
    );
  }

  ngOnInit() {
    console.log("date" + this.d);
    this.firm();
    this.category()
  }
  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  public setImage(imageName: string) {
    this.image = imageName;
  }

  public category(){
    this.subscriptions.add(this.productService.getCategories()
      .subscribe((categ) =>{
        this.responseCategory = categ;
        this.categoryName = this.responseCategory;
      }));
  }

  public firm(){
    this.subscriptions.add(this.productService.getFirm()
      .subscribe((firm) =>{
        this.responseFirm = firm;
        this.firmName = this.responseFirm;
      }));
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
      shippingDate: o.shippingDate,
      categoryName: o.categoryName,
      description: o.description,
    };
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.form.disable();
    this.loading = true;
    let product = this.mapToProduct(this.form.value);
    product.image = this.image;
    this.productService.AddProduct(product)
      .pipe(first())
      .subscribe({
        next: (res) => {
          console.log("Product added");
          this.router.navigateByUrl('/products/' + res.id);
          this.loading = false;
          this.registered = true;
          this.alertService.addAlert("Product was successfully added!", AlertType.Success);
        },
        error: (error) => {
          console.log(error);
          this.form.enable();
          this.loading = false;
          this.alertService.addAlert("Error has occurred during creation", AlertType.Danger);
        }
      });
  }
}
