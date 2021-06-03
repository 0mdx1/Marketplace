import { Component, Input } from '@angular/core';
import { StaffMember } from 'src/app/_models/staff-member';

@Component({
  selector: 'app-manager-list',
  templateUrl: './manager-list.component.html',
  styleUrls: ['./manager-list.component.css'],
})
export class ManagerListComponent {
  @Input() managers: StaffMember[] = [];
  readonly status_list: string[] = ['all', 'active', 'terminated'];
}
