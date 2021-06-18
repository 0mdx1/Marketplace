import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../_auth/auth.service";
import { CartItem } from "../_models/cart-item.model";
import { User } from "../_models/user";
import { BrowserCart } from "../_services/cart/browser-cart";
import { CartService } from "../_services/cart/cart.service";
import { Checkout } from "../_services/checkout/checkout.service";
import {catchError} from "rxjs/operators";
import {HttpErrorHandlerService} from "../_services/http-error-handler.service";
import {Router} from "@angular/router";

@Component({
  selector: 'mg-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  items: CartItem[] = [];
  delivery: Array<any> = [];
  orderDetailsForm: FormGroup;
  submitted = false;
  authUser: User = {};
  isVisibleBanner = true;

  deliveryTimes: string[] = []
  freeCouriers = false;

  constructor(
    private cartService: CartService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private checkoutService: Checkout,
    private browserCart: BrowserCart,
    private errorHandler: HttpErrorHandlerService,
    private router: Router
  ) {
    this.orderDetailsForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      phone: ['', [Validators.pattern(/\+380[0-9]{9}/), Validators.required]],
      address: ['', Validators.required],
      deliveryDay: ['', Validators.required],
      deliveryTime: ['', Validators.required],
      comment: [''],
      disturb: [false]
    })
  }

  ngOnInit(): void {
    this.items = this.cartService.getCart().getItems();
    this.getAuthUserInfo();
    this.checkoutService.getDeliveryTime()
    .pipe(
      catchError(err => {
        return this.errorHandler.displayValidationError(err);
      })
    )
    .subscribe(
      data => {
        this.freeCouriers = true;
        let splittedData: string[][] = [];
        data.forEach(elem => splittedData.push(elem.split('T')));
        

        let prevDate = '';
        let arrtimes = [];

        for(let i = 0; i < splittedData.length; i++) {
          if(splittedData[i][0] !== prevDate) {
            arrtimes = [];
            prevDate = splittedData[i][0];
            arrtimes.push(splittedData[i][1]);
            let obj = {
              date: prevDate,
              times: arrtimes
            }
            this.delivery.push(obj);
          }else {
            arrtimes.push(splittedData[i][1]);
          }
        }
      }
    );
  }

  getSubtotalPrice(cartItem: CartItem): number {
    return cartItem.quantity*this.getPrice(cartItem);
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice: number = 0;
    cartItems.forEach( cartItem => {
      totalPrice+=this.getSubtotalPrice(cartItem);
    })
    return totalPrice;
  }

  getTotalDiscount(cartItem: CartItem[]): number {
    let totalDiscount: number = 0;
    cartItem.forEach(item => {
      totalDiscount += item.goods.discount;
    });
    return totalDiscount;
  }

  getPrice(cartItem: CartItem): number{
    return cartItem.goods.price-cartItem.goods.price*(cartItem.goods.discount/100);
  }

  isAuth(): boolean {
    return this.authService.isAuthenticated();
  }

  get getForm(): { [p: string]: AbstractControl } { return this.orderDetailsForm.controls; }

  onSubmit(): void {
    if(this.isAuth()) {
      this.orderDetailsForm.patchValue({
        name: this.authUser.name,
        surname: this.authUser.surname,
        phone: this.authUser.phone
      });
    }

    if(this.orderDetailsForm.invalid) {
      return;
    }
    this.submitted = true;

  }

  private getAuthUserInfo() {
    if(this.isAuth()) {
      this.checkoutService.getUser()
        .subscribe((user: User) => this.authUser=user);

    }
  }

  doOrder() {
    const mappedItems: any[] = [];
    this.items.map(item => {
      mappedItems.push({
        categoryName: item.goods.categoryName,
        description: item.goods.description,
        discount: item.goods.discount,
        firmName: item.goods.firmName,
        goodName: item.goods.goodName,
        goodsId: item.goods.id,
        inStock: item.goods.inStock,
        price: item.goods.price,
        unit: item.goods.unit,
        quantity: item.quantity,
        addingTime: item.addingTime
      })
    })


    const receiveObj = {
      name: this.orderDetailsForm.value['name'],
      surname: this.orderDetailsForm.value['surname'],
      phone: this.orderDetailsForm.value['phone'],
      address: this.orderDetailsForm.value['address'],
      deliveryTime: this.orderDetailsForm.value['deliveryDay'] + 'T' + (this.orderDetailsForm.value['deliveryTime'] + ':00'),
      comment: this.orderDetailsForm.value['comment'],
      disturb: this.orderDetailsForm.value['disturb'],
      totalSum: this.getTotalPrice(this.items),
      discountSum: this.getTotalPrice(this.items) - this.getTotalDiscount(this.items),
      items: mappedItems,
    }
    
    this.checkoutService.sendOrderDetails(receiveObj)
      .pipe(
        catchError(err => {
          return this.errorHandler.displayValidationError(err);
        })
      )
      .subscribe(
        () => {
          this.submitted = false;
          this.browserCart.empty();
          this.items = [];
          console.log("Succes!")
        },
        (msg) => {
          this.router.navigateByUrl('/cart')
        }
    );

  }

  hideBanner() {
    this.isVisibleBanner = false;
  } 

  getDeliveryTimes(): boolean {
    for(let i = 0; i < this.delivery.length; i++) {
      if(this.delivery[i].date == this.orderDetailsForm.value['deliveryDay']) {
        this.deliveryTimes = this.delivery[i].times;
        return true;
      }
    }
    return false;
  }
}
