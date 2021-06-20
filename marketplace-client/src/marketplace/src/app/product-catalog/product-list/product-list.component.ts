import {  Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import {AuthService} from "../../_auth/auth.service";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit{
  @Input() products: Product[] = [];
  direction: string = "ASC";
  sort: string = "name";
  role: string | null = null;

  constructor(private authService: AuthService) {

  }
  ngOnInit(){
    this.role = this.authService.getRole();
  }

  setDirection():void{
    if(this.direction == "ASC"){
      this.direction = "DESC";
    }
    else{
      this.direction = "ASC";
    }
  }

  setSort(value:string):void{
    this.sort=value;
  }
}
