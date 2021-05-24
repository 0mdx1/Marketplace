import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
} from '@angular/core';
import { Courier } from 'src/app/_models/courier';
import { CourierService } from 'src/app/_services/courier.service';

@Component({
  selector: 'app-courier-filter',
  templateUrl: './courier-filter.component.html',
  styleUrls: ['./courier-filter.component.css'],
})
export class CourierFilterComponent implements OnInit {
  activeRadioButton = '';
  status_list: string[] = ['all', 'active', 'inactive', 'disabled'];

  @Output() results: EventEmitter<Courier[]> = new EventEmitter<Courier[]>();

  constructor(private courierService: CourierService, private el: ElementRef) {}

  ngOnInit(): void {
    this.activeRadioButton = this.courierService.getFilters();

    this.courierService
      .filterCouriers(this.activeRadioButton)
      .subscribe((results: Courier[]) => {
        this.results.emit(results);
      });
  }

  filterStatus(e: any) {
    this.courierService
      .filterCouriers(e.target.value)
      .subscribe((results: Courier[]) => {
        this.results.emit(results);
      });
  }
}
