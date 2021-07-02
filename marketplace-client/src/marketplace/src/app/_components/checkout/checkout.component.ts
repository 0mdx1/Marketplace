import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';

import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../_auth/auth.service';
import { CartItem } from '../../_models/cart-item.model';
import { User } from '../../_models/user';
import { CartService } from '../../_services/cart/cart.service';
import { Checkout } from '../../_services/checkout/checkout.service';

import { HttpErrorHandlerService } from '../../_services/http-error-handler.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../../_services/alert.service';
import { AlertType } from '../../_models/alert';
import { OrderItemModel } from '../../_models/order/order-item.model';
import { OrderModel } from '../../_models/order/order.model';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnInit, AfterViewInit {
  items: CartItem[] = [];
  orderDetailsForm: FormGroup;
  submitted = false;
  showOrderDetails = false;
  authUser: User = {};
  deliveryDateTime: Date[] = [];
  deliveryTimes: Date[] = [];
  @ViewChild('content') content: any;

  allDeliveryDates: Date[] = [];
  deliveryDistinctDays: Date[] = [];

  freeCouriers = false;

  constructor(
    private cartService: CartService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private checkoutService: Checkout,
    private errorHandler: HttpErrorHandlerService,
    private router: Router,
    private modalService: NgbModal,
    private alertService: AlertService
  ) {
    this.orderDetailsForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      phone: ['', [Validators.pattern(/^\+380[0-9]{9}$/), Validators.required]],
      address: ['', Validators.required],
      deliveryDay: ['', Validators.required],
      deliveryTime: ['', Validators.required],
      comment: [''],
      disturb: [false],
    });
  }

  ngAfterViewInit(): void {
    if (!this.isAuth()) {
      this.openModal();
    }
  }

  openModal(): void {
    this.modalService.open(this.content, { centered: true });
  }

  ngOnInit(): void {
    this.items = this.cartService.getCart().getItems();
    this.getAuthUserInfo();
    this.checkoutService.getDeliveryTime().subscribe((data) => {
      this.freeCouriers = true;
      this.allDeliveryDates = data;
      this.deliveryDistinctDays = this.getDistinctDays();
    });
  }

  getDistinctDays(): Date[] {
    let prevDate = new Date(0);
    const distinctDates: Date[] = [];
    this.allDeliveryDates.forEach((elem) => {
      if (
        prevDate.getFullYear() !== elem.getFullYear() ||
        prevDate.getMonth() !== elem.getMonth() ||
        prevDate.getDate() !== elem.getDate()
      ) {
        distinctDates.push(elem);
        prevDate = elem;
      }
    });
    return distinctDates;
  }

  calculateDeliveryTimes(): boolean {
    this.deliveryTimes = [];
    const chosenDate = new Date(this.orderDetailsForm.value.deliveryDay);
    this.allDeliveryDates.forEach((elem) => {
      if (
        elem.getFullYear() === chosenDate.getFullYear() &&
        elem.getMonth() === chosenDate.getMonth() &&
        elem.getDate() === chosenDate.getDate()
      ) {
        this.deliveryTimes.push(elem);
      }
    });
    return true;
  }

  getSubtotalPrice(cartItem: CartItem): number {
    return cartItem.quantity * this.getPrice(cartItem);
  }

  getTotalPrice(cartItems: CartItem[]): number {
    let totalPrice = 0;
    cartItems.forEach((cartItem) => {
      totalPrice += this.getSubtotalPrice(cartItem);
    });
    return totalPrice;
  }

  getTotalDiscount(cartItem: CartItem[]): number {
    let totalDiscount = 0;
    cartItem.forEach((item) => {
      totalDiscount += item.goods.discount;
    });
    return totalDiscount;
  }

  getPrice(cartItem: CartItem): number {
    return (
      cartItem.goods.price -
      cartItem.goods.price * (cartItem.goods.discount / 100)
    );
  }

  isAuth(): boolean {
    return this.authService.isAuthenticated();
  }

  get getForm(): { [p: string]: AbstractControl } {
    return this.orderDetailsForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.isAuth()) {
      this.orderDetailsForm.patchValue({
        name: this.authUser.name,
        surname: this.authUser.surname,
        phone: this.authUser.phone,
      });
    }

    if (this.orderDetailsForm.invalid) {
      return;
    }
    this.showOrderDetails = true;
  }

  private getAuthUserInfo(): void {
    if (this.isAuth()) {
      this.checkoutService
        .getUser()
        .subscribe((user: User) => (this.authUser = user));
    }
  }

  doOrder(): void {
    const mappedItems: OrderItemModel[] = [];
    this.items.map((item) => {
      mappedItems.push(new OrderItemModel(item));
    });

    const order: OrderModel = new OrderModel(
      mappedItems,
      this.getTotalPrice(this.items),
      this.getTotalPrice(this.items) - this.getTotalDiscount(this.items),
      this.orderDetailsForm,
      this.formDeliveryDate()
    );

    this.checkoutService.sendOrderDetails(order).subscribe(
      () => {
        this.submitted = false;
        this.cartService.getCart().empty();
        this.items = [];
        this.alertService.addAlert('Order sent!', AlertType.Success);
      },
      () => {
        this.router.navigateByUrl('/cart');
      }
    );
  }

  formDeliveryDate(): Date {
    return new Date(this.orderDetailsForm.value.deliveryTime);
  }
}
