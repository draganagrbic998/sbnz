import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Notification } from 'src/app/models/notification';
import { NotificationService } from 'src/app/services/notification.service';
import { DeleteConfirmationComponent } from '../../common/delete-confirmation/delete-confirmation.component';
import { DeleteData } from 'src/app/models/delete-data';

@Component({
  selector: 'app-notification-details',
  templateUrl: './notification-details.component.html',
  styleUrls: ['./notification-details.component.scss']
})
export class NotificationDetailsComponent implements OnInit {

  constructor(
    private notificationService: NotificationService,
    private dialog: MatDialog
  ) { }

  @Input() notification: Notification = {} as Notification;
  @Input() index = 0;

  delete(): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.notificationService.delete(this.notification.id),
      refreshFunction: () => this.notificationService.announceRefreshData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
  }

}
