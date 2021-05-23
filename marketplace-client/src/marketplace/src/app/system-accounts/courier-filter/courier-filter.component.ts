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
  searchText = "";
  currentPage = 0;
  pageNum = 0;
  status_list: string[] = ['all', 'active', 'inactive'];

  @Output() results: EventEmitter<Courier[]> = new EventEmitter<Courier[]> ();

  constructor(private courierService: CourierService,
              private el: ElementRef) { }

  ngOnInit(): void {
    /*this.courierService.getFilters().subscribe(
      filter => this.activeRadioButton = filter
    );*/

    this.courierService.getPageNum().subscribe(
      pageNum => this.pageNum = pageNum
    );

    /*this.courierService.getPage().subscribe(
      page => this.currentPage = page
    );*/
    this.activeRadioButton = this.courierService.getFilters();
    this.currentPage = this.courierService.getPage();
    this.searchText = this.courierService.getSearch();

    this.courierService.filterCouriers(this.activeRadioButton, this.searchText, this.currentPage)
    .subscribe(
      (results: Courier[]) => {
        this.results.emit(results);
      }
    );

    const obs = fromEvent(this.el.nativeElement, 'keyup')
      .pipe (
        map((e: any) => e.target.value),
        //filter((text:string) => text.length>0),
        debounceTime(250),
        map((query:string) => {
          console.log(query);
          this.searchText = query;
          return this.courierService.filterCouriers(this.activeRadioButton, query, this.currentPage)
        }) ,
        switchAll()
      );
      obs.subscribe(
        (results: Courier[]) => {
          this.results.emit(results);
        }
      );


    /*this.courierService.filterCouriers(this.activeRadioButton).subscribe(
      (results: Courier[]) => {
        this.results.emit(results);
      }
    );*/
  }

  filterStatus(e: any) {
    this.courierService.filterCouriers(e.target.value, this.searchText, this.currentPage).subscribe(
      (results: Courier[]) => {
        this.results.emit(results);
      }
    );
  }

}
