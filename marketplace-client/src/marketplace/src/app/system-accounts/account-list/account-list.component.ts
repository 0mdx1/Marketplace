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
    // private helper: AuthService
  ) {}

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  getStatusList() {
    // this.helper.getMail(); //!!!!
    return this.service.getStatusList();
  }

  createUser() {
    this.service.navigateToRegisterStaff();
  }
  changeInfo(id:number, role:string){
    if(role.localeCompare("ROLE_PRODUCT_MANAGER" )) {
      this.service.navigateToUpdatedStaff(id, 1);
    }
    else if (role.localeCompare("ROLE_COURIER")){
      this.service.navigateToUpdatedStaff(id, 2);
    }
  }
}
