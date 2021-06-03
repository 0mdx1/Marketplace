import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StaffMember } from 'src/app/_models/staff-member';
import { UserDto } from 'src/app/_models/UserDto';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  activeRadioButton = '';
  users: StaffMember[] = [];
  @Input() status_list: string[] = [];

  //@Output() results: EventEmitter<User[]> = new EventEmitter<User[]>();
  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: SystemAccountService) {}

  ngOnInit(): void {
    this.activeRadioButton = this.getFilter();
    this.filterStatus(this.activeRadioButton, true);
  }

  filterStatus(status: string, init: boolean) {
    this.service
      .getFilteredUsers(status, init)
      .subscribe((results: UserDto) => {
        this.users = results.users;
        this.results.emit();
        this.service.pageNumSource.next(results.pageNum);
        this.service.pageSource.next(results.currentPage);
      });
  }

  onChange(e: any) {
    this.filterStatus(e.target.value, false);
  }

  getFilter(): string {
    let filter = this.service.getFilter();
    if (!this.status_list.includes(filter)) {
      filter = this.status_list[0];
    }
    return filter;
  }

  getUsers(): StaffMember[] {
    return this.users;
  }
}
