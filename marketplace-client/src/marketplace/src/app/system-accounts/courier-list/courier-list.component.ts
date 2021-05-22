import { Component, OnInit } from '@angular/core';
import { Courier } from 'src/app/_models/courier';
import { CourierService } from 'src/app/_services/courier.service';
import { CourierFilterComponent } from '../courier-filter/courier-filter.component';

@Component({
  selector: 'app-courier-list',
  templateUrl: './courier-list.component.html',
  styleUrls: ['./courier-list.component.css']
})
export class CourierListComponent implements OnInit {

  couriers: Courier[] = [];

  constructor(private courierService: CourierService) { }

  ngOnInit(): void {
    console.log('Courier list component');
    this.getCouriers();
  }

  getCouriers(): void {
    this.courierService.getCouriers()
      .subscribe(couriers => this.couriers = couriers);
  }



}
