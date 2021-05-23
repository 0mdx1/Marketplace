import { Component, ElementRef, EventEmitter, OnInit, Output } from '@angular/core';
import { fromEvent } from 'rxjs';
import { debounceTime, filter, map, switchAll } from 'rxjs/operators';
import { Courier } from 'src/app/_models/courier';
import { CourierService } from 'src/app/_services/courier.service';

@Component({
  selector: 'app-courier-filter',
  templateUrl: './courier-filter.component.html',
  styleUrls: ['./courier-filter.component.css']
})
export class CourierFilterComponent implements OnInit {

  activeRadioButton = "";
  search: string = "";
  status_list: string[] = ['all', 'active', 'inactive'];

  @Output() results: EventEmitter<Courier[]> = new EventEmitter<Courier[]> ();

  constructor(private courierService: CourierService,
              private el: ElementRef) { }

  ngOnInit(): void {

    this.activeRadioButton = this.courierService.getFilters();
    

    /*const obs = fromEvent(this.el.nativeElement, 'keyup')
      .pipe (
        map((e: any) => e.target.value),
        //filter((text:string) => text.length>0),
        debounceTime(250),
        map((query:string) => {
          console.log('here'+query);
          this.search = query;
          return this.courierService.searchCouriers(this.search);
        }) ,
        switchAll()
      );
      obs.subscribe(
        (results: Courier[]) => {
          this.results.emit(results);
        }
      );

      this.search = this.courierService.getSearch();*/

    this.courierService.filterCouriers(this.activeRadioButton)
    .subscribe(
      (results: Courier[]) => {
        this.results.emit(results);
      }
    );
  }

  filterStatus(e: any) {
    this.courierService.filterCouriers(e.target.value).subscribe(
      (results: Courier[]) => {
        this.results.emit(results);
      }
    );
  }

}
