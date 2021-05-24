import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
} from '@angular/core';
import { User } from 'src/app/_models/user';
import { ManagerService } from 'src/app/_services/manager.service';

@Component({
  selector: 'app-manager-filter',
  templateUrl: './manager-filter.component.html',
  styleUrls: ['./manager-filter.component.css'],
})
export class ManagerFilterComponent implements OnInit {
  activeRadioButton = '';
  status_list: string[] = ['all', 'enabled', 'disabled'];

  @Output() managers: EventEmitter<User[]> = new EventEmitter<User[]>();

  constructor(private managerService: ManagerService, private el: ElementRef) {}

  ngOnInit(): void {
    this.activeRadioButton = this.managerService.getFilters();

    this.managerService
      .filterManagers(this.activeRadioButton)
      .subscribe((results: User[]) => {
        this.managers.emit(results);
      });
  }

  filterStatus(e: any) {
    this.managerService
      .filterManagers(e.target.value)
      .subscribe((results: User[]) => {
        this.managers.emit(results);
      });
  }
}
