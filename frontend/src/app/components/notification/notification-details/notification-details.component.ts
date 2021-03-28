import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Notification } from 'src/app/models/notification';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { DeleteConfirmationComponent } from '../../utils/delete-confirmation/delete-confirmation.component';

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
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.notificationService.delete(this.notification.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.notificationService.announceRefreshData();
      }
    });
  }

  ngOnInit(): void {
  }

}
