import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE, LAST_PAGE } from 'src/app/constants/pagination';
import { Notification } from 'src/app/models/notification';
import { Pagination } from 'src/app/models/pagination';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {

  constructor(
    private notificationService: NotificationService
  ) { }

  notifications: Notification[] = [];
  fetchPending = true;
  pagination: Pagination = {
    firstPage: true,
    lastPage: true,
    pageNumber: 0
  };

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchNotifications();
  }

  fetchNotifications(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.notificationService.findAll(this.pagination.pageNumber).subscribe(
      (data: HttpResponse<Notification[]>) => {
        this.fetchPending = false;
        if (data){
          this.notifications = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE) === 'false' ? false : true;
        }
        else{
          this.notifications = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.notificationService.refreshData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber -= 1 : 0;
      this.changePage(0);
    });
  }
}
