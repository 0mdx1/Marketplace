import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/products/product';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent {
  @Input() products: Product[] = [];
  direction: string = "ASC";
  sort: string = "name";

  ngOnInit(){
    this.direction = "ASC";
    this.sort="name";
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
