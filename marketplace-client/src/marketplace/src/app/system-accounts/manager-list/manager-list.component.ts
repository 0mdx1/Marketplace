import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';

@Component({
  selector: 'app-manager-list',
  templateUrl: './manager-list.component.html',
  styleUrls: ['./manager-list.component.css']
})
export class ManagerListComponent implements OnInit {

  @Input() managers: User[] = [];
  constructor() { }

  ngOnInit(): void {
  }

}
