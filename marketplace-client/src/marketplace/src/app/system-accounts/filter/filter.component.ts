import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { StaffMember } from 'src/app/_models/staff-member';
import { UserDto } from 'src/app/_models/UserDto';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit, OnDestroy {
  activeRadioButton = '';
  users: StaffMember[] = [];
  type: string = 'courier';
  typeList: string[] = ['managers', 'couriers'];
  subscription!: Subscription;
  @Input() statusList: string[] = [];
  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: SystemAccountService) {}

  ngOnInit(): void {
    this.activeRadioButton = this.getFilter();
    this.filterStatus(this.activeRadioButton, true);
    this.type = this.service.getType();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private filterStatus(status: string, init: boolean) {
    this.subscription = this.service
      .getFilteredUsers(status, init)
      .subscribe((results: UserDto) => {
        this.users = results.users;
        this.results.emit();
      });
  }

  onChange(e: any) {
    this.filterStatus(e.target.value, false);
  }

  private getFilter(): string {
    let filter = this.service.getFilter();
    if (!this.statusList.includes(filter)) {
      filter = this.statusList[0];
    }
    return filter;
  }

  getUsers(): StaffMember[] {
    return this.users;
  }

  setType(type: string): void {
    this.type = type;
    this.service.changeType(type);
  }
}
