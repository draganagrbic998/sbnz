import { Component, OnInit } from '@angular/core';
import { Notification } from 'src/app/models/notification';
import { Pagination } from 'src/app/models/pagination';
import { NotificationService } from 'src/app/services/notification.service';
import { Page } from 'src/app/models/page';

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
  pending = true;
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
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.notificationService.findAll(this.pagination.pageNumber).subscribe(
      (page: Page<Notification>) => {
        this.pending = false;
        this.notifications = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
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
