import { Component, OnInit } from '@angular/core';
import { CourierService } from 'src/app/_services/courier.service';

@Component({
  selector: 'app-courier-filter',
  templateUrl: './courier-filter.component.html',
  styleUrls: ['./courier-filter.component.css']
})
export class CourierFilterComponent implements OnInit {

  constructor(private courierService: CourierService) { }

  ngOnInit(): void {
  }

  filterStatus(e: any) {
    console.log('Radio button value ' + e.target.value);
    this.courierService.filterCouriers(e.target.value);
  }

}
