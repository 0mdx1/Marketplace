import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  ElementRef,
  Input,
} from '@angular/core';
import { fromEvent } from 'rxjs';
import { debounceTime, map, switchAll } from 'rxjs/operators';
import { Courier } from 'src/app/_models/courier';
import { User } from 'src/app/_models/user';
import { CourierService } from 'src/app/_services/courier.service';
import { ManagerService } from 'src/app/_services/manager.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  @Output() results: EventEmitter<User[]> = new EventEmitter<User[]>();
  @Output() couriers: EventEmitter<Courier[]> = new EventEmitter<Courier[]>();
  @Input() userType = '';

  readonly COURIER: string = 'courier';
  readonly MANAGER: string = 'manager';

  search: string = '';

  constructor(
    private managerService: ManagerService,
    private courierService: CourierService,
    private el: ElementRef
  ) {}

  ngOnInit(): void {
    console.log('manager search component');
    const obs = fromEvent(this.el.nativeElement, 'keyup').pipe(
      map((e: any) => e.target.value),
      //filter((text:string) => text.length>0),
      debounceTime(250),
      map((query: string) => {
        console.log(query);
        if (this.userType == this.MANAGER) {
          return this.managerService.searchManagers(query);
        } else {
          return this.courierService.searchCouriers(query);
        }
      }),
      switchAll()
    );
    if (this.userType == this.MANAGER) {
      obs.subscribe((results: User[]) => {
        console.log('emit search');
        this.results.emit(results);
      });
      this.search = this.managerService.getSearch();
    } else {
      obs.subscribe((couriers: Courier[]) => {
        console.log('emit search');
        this.couriers.emit(couriers);
      });
      this.search = this.courierService.getSearch();
    }
  }
}
