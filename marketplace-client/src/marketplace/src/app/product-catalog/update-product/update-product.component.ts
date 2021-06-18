import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductService} from "../../_services/product.service";
import { Product } from '../../_models/products/product';

import {first, map, startWith} from "rxjs/operators";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css'],
})
export class UpdateProductComponent implements OnInit{


  //form: FormGroup;
  form = new FormGroup({
    name: new FormControl('')
  });

  myControl = new FormControl();
  submitted = false;

  categories: string[] = [];

  products: Product[] = [];
  categorySubscription!: Subscription;


  categoryName: string[]= ["fruits", "vegetables", "meat", "drinks", "water"];
  inStock: string[] = ["true", "false"];
  unit: string[] = ["KILOGRAM", "ITEM", "LITRE"];
  status: string[] = ["true", "false"]


  loading = false;

  updated = false;

  response: any;



  ngOnInit(){

    //.subscribe((response) => {
      this.accountService.getProductInfo(this.route.snapshot.params.id)
        .subscribe((response) => {
          this.response = response;
          console.log(this.response);
          this.formCreation();
        })

  }

  private getCategories() {
    this.categorySubscription = this.service
      .getCategories()
      .subscribe((results: string[]) => {
        this.categories = results;
      });
  }

  constructor(
    private service: ProductService,
    private formBuilder: FormBuilder,
    private accountService: ProductService,
    private route: ActivatedRoute,
  ) {}

  formCreation(){
    this.form = this.formBuilder.group(
      {
        goodName: [this.response.goodName, Validators.required],
        firmName: [this.response.firmName, Validators.required],
        quantity: [this.response.quantity, [Validators.min(1), Validators.required]],
        price: [this.response.price, [Validators.min(1), Validators.required]],
        unit: [this.response.unit, Validators.required],
        discount: [this.response.discount, [Validators.min(1), Validators.required]],
        inStock: [String(this.response.inStock), Validators.required],
        status: [String(this.response.status), Validators.required],
        categoryName: [this.response.categoryName, Validators.required],
        description: [this.response.description, Validators.required],
      },
    );
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.form.controls;
  }

  private mapToProduct(o: any): Product {
    return {
      id: -1,
      goodName: o.goodName,
      firmName: o.firmName,
      quantity: o.quantity,
      price: o.price,
      unit: o.unit,
      image: "",
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
      return;
    }
    this.loading = true;
    let observable = null;

      observable = this.accountService.updateProduct(
        this.mapToProduct(this.form.value), (this.route.snapshot.params.id)
      );

    console.log(this.mapToProduct(this.form.value))

    observable.pipe(first()).subscribe({
      next: () => {
        this.loading = false;
        this.updated = true;
      },
    });
  }


}
