import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StaffMember } from 'src/app/_models/staff-member';
import { UserDto } from 'src/app/_models/UserDto';
import { SystemAccountService } from 'src/app/_services/system-account.service';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent implements OnInit {
  currentPage: number = 1;
  pageNum: number = 1; //total number of pages
  users: StaffMember[] = [];
  @Output() results: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: SystemAccountService) {}

  ngOnInit(): void {
    this.service.pageNumSource.subscribe((pageNum) => {
      this.pageNum = pageNum;
    });
    this.service.pageSource.subscribe((page) => {
      this.currentPage = page;
    });
    this.currentPage = this.service.getCurrentPage();

    this.service
      .getPagedUsers(this.currentPage)
      .subscribe((results: UserDto) => {
        //this.results.emit(results.users);
        this.users = results.users;
        this.results.emit();
        this.pageNum = results.pageNum;
        this.currentPage = results.currentPage;
      });

    if (Number.isNaN(this.currentPage) || this.currentPage < 1) {
      this.currentPage = 1;
    }
    if (this.currentPage > this.pageNum) {
      this.currentPage = this.pageNum;
    }
  }

  nextPage(): void {
    this.currentPage = this.currentPage + 1;
    this.getPage();
  }

  prevPage(): void {
    this.currentPage = this.currentPage - 1;
    this.getPage();
  }

  private getPage(): void {
    this.service
      .getPagedUsers(this.currentPage)
      .subscribe((results: UserDto) => {
        //this.results.emit(results.users);
        this.users = results.users;
        this.results.emit();
        this.pageNum = results.pageNum;
        this.currentPage = results.currentPage;
      });
  }

  getUsers(): StaffMember[] {
    return this.users;
  }
}
