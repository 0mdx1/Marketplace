import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { Courier } from 'src/app/_models/courier';

@Component({
  selector: 'app-courier-list',
  templateUrl: './courier-list.component.html',
  styleUrls: ['./courier-list.component.css'],
})
export class CourierListComponent implements OnInit, AfterViewInit {
  @Input() couriers: Courier[] = [];

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }
}
