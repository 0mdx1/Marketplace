import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { StaffMember } from 'src/app/_models/staff-member';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css'],
})
export class AccountListComponent {
  @Input() users: StaffMember[] = [];
  constructor(
    private cdr: ChangeDetectorRef,
    private service: SystemAccountService
  ) {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  getStatusList() {
    return this.service.getStatusList();
  }

  createUser() {
    this.service.navigateToRegisterStaff();
  }
}
