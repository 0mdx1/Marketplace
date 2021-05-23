import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Courier } from 'src/app/_models/courier';
import { User } from 'src/app/_models/user';
import { CourierService } from 'src/app/_services/courier.service';
import { ManagerService } from 'src/app/_services/manager.service';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent implements OnInit {
  currentPage: number = 1;
  pageNum: number = 1; //total number of pages

  readonly MANAGER: string = 'manager';
  readonly COURIER: string = 'courier';

  @Input() userType = '';
  @Output() results: EventEmitter<Courier[]> = new EventEmitter<Courier[]>();
  @Output() managers: EventEmitter<User[]> = new EventEmitter<User[]>();

  constructor(
    private courierService: CourierService,
    private managerService: ManagerService
  ) {}

  ngOnInit(): void {
    if (this.userType == this.COURIER) {
      this.courierService.getPageNum().subscribe((pageNum) => {
        this.pageNum = pageNum;
      });
      this.currentPage = this.courierService.getPage();
    } else {
      this.managerService.getPageNum().subscribe((pageNum) => {
        this.pageNum = pageNum;
      });
      this.currentPage = this.managerService.getPage();
    }

    if (Number.isNaN(this.currentPage) || this.currentPage < 1) {
      this.currentPage = 1;
    }
    if (this.currentPage > this.pageNum) {
      this.currentPage = this.pageNum;
    }

    if (this.userType == this.COURIER) {
      this.courierService
        .pageCouriers(this.currentPage)
        .subscribe((results: Courier[]) => {
          this.results.emit(results);
        });
    } else {
      this.managerService
        .pageManagers(this.currentPage)
        .subscribe((managers: User[]) => {
          this.managers.emit(managers);
        });
    }
  }

  nextPage(): void {
    this.currentPage = this.currentPage + 1;
    this.getPage(this.userType);
  }

  prevPage(): void {
    this.currentPage = this.currentPage - 1;
    this.getPage(this.userType);
  }

  getPage(userType: string): void {
    console.log(userType);
    if (userType == this.COURIER) {
      this.courierService
        .pageCouriers(this.currentPage)
        .subscribe((results: Courier[]) => {
          this.results.emit(results);
        });
    } else if (userType == this.MANAGER) {
      this.managerService
        .pageManagers(this.currentPage)
        .subscribe((managers: User[]) => {
          this.managers.emit(managers);
        });
    }
  }
}
