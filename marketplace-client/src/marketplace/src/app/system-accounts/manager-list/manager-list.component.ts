import { Component, Input } from '@angular/core';
import { User } from 'src/app/_models/user';

@Component({
  selector: 'app-manager-list',
  templateUrl: './manager-list.component.html',
  styleUrls: ['./manager-list.component.css'],
})
export class ManagerListComponent {
  @Input() managers: User[] = [];
  readonly status_list: string[] = ['all', 'active', 'inactive'];
}
