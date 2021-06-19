import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductService} from "../../_services/product.service";
import { Product } from '../../_models/products/product';

import {first} from "rxjs/operators";
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

  subscriptions: Subscription = new Subscription();

  categoryName: string[]= [""];
  firmName: string[]=[""];
  unit: string[] = ["KILOGRAM", "ITEM", "LITRE"];
  status: string[] = ["true", "false"];

  submitted = false;
  loading = false;
  updated = false;

  response: any;
  responseCategory: any;
  responseFirm: any;
  image: string = '';

  ngOnInit(){
    this.subscriptions.add(this.accountService.getProductInfo(this.route.snapshot.params.id)
      .subscribe((response) => {
        this.response = response;
        this.formCreation();
        this.firm()
        this.category();
      }));
  }
  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  public setImage(imageName: string){
    this.image = imageName;
  }

  private category(){
    this.subscriptions.add( this.accountService.getCategories()
      .subscribe((categ) =>{
        this.responseCategory = categ;
        this.categoryName = this.responseCategory;
      }));
  }

  private firm(){
    this.subscriptions.add( this.accountService.getFirm()
      .subscribe((firm) =>{
        this.responseFirm = firm;
        this.firmName = this.responseFirm;
      }));
  }

  constructor(
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
      id: this.response.id,
      goodName: o.goodName,
      firmName: o.firmName,
      quantity: o.quantity,
      price: o.price,
      unit: o.unit,
      status: o.status,
      image: o.image,
      discount: o.discount,
      inStock: o.inStock,
      shippingDate: this.response.shippingDate,
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
    observable = this.accountService.updateProduct(
      product, (this.route.snapshot.params.id)
    );

    observable.pipe(first()).subscribe({
      next: () => {
        this.loading = false;
        this.updated = true;
      },
    });
  }


}
