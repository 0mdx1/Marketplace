import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  Input,
} from '@angular/core';
import { Courier } from 'src/app/_models/courier';

@Component({
  selector: 'app-courier-list',
  templateUrl: './courier-list.component.html',
  styleUrls: ['./courier-list.component.css'],
})
export class CourierListComponent implements AfterViewInit {
  @Input() couriers: Courier[] = [];

  readonly status_list = ['all', 'active', 'inactive', 'terminated'];
  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }
}
