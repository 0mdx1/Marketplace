import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  ElementRef,
  OnDestroy,
} from '@angular/core';
import { fromEvent, merge, Subscription } from 'rxjs';
import { debounceTime, map, switchAll } from 'rxjs/operators';
import { StaffMember } from 'src/app/_models/staff-member';
import { UserDto } from 'src/app/_models/UserDto';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit, OnDestroy {
  @Output() results: EventEmitter<any> = new EventEmitter<any>();
  users: StaffMember[] = [];
  search: string = '';
  init: boolean = true;
  subscription!: Subscription;

  constructor(private service: SystemAccountService, private el: ElementRef) {}

  ngOnInit(): void {
    this.search = this.service.getSearch();

    this.subscription = merge(
      fromEvent(this.el.nativeElement, 'keyup'),
      fromEvent(this.el.nativeElement, 'search')
    )
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
        //this.results.emit(results.users);
        this.users = results.users;
        this.results.emit();
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getUsers(): StaffMember[] {
    return this.users;
  }
}
