import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  Input,
} from '@angular/core';
import { User } from 'src/app/_models/user';

@Component({
  selector: 'app-courier-list',
  templateUrl: './courier-list.component.html',
  styleUrls: ['./courier-list.component.css'],
})
export class CourierListComponent implements AfterViewInit {
  @Input() couriers: User[] = [];

  readonly status_list = ['all', 'active', 'inactive', 'terminated'];
  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }
}
