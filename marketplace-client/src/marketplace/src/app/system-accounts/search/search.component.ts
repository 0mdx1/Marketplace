import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  ElementRef,
} from '@angular/core';
import { fromEvent } from 'rxjs';
import { debounceTime, map, switchAll } from 'rxjs/operators';
import { StaffMember } from 'src/app/_models/staff-member';
import { UserDto } from 'src/app/_models/UserDto';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  //@Output() results: EventEmitter<User[]> = new EventEmitter<User[]>();
  @Output() results: EventEmitter<any> = new EventEmitter<any>();
  users: StaffMember[] = [];
  search: string = '';
  init: boolean = true;

  constructor(private service: SystemAccountService, private el: ElementRef) {}

  ngOnInit(): void {
    this.search = this.service.getSearch();

    fromEvent(this.el.nativeElement, 'keyup')
      .pipe(
        map((e: any) => e.target.value),
        //filter((text:string) => text.length>0),
        debounceTime(250),
        map((query: string) => {
          const obs = this.service.getSearchedUsers(query, this.init);
          this.init = false;
          return obs;
        }),
        switchAll()
      )
      .subscribe((results: UserDto) => {
        console.log('emit search');
        //this.results.emit(results.users);
        this.users = results.users;
        this.results.emit();
        this.service.pageNumSource.next(results.pageNum);
        this.service.pageSource.next(results.currentPage);
      });
  }

  getUsers(): StaffMember[] {
    return this.users;
  }

  createUser() {
    this.service.navigateToRegisterStaff();
  }
}
