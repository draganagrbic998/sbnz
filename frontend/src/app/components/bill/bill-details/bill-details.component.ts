import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatDrawer } from '@angular/material/sidenav';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Bill } from 'src/app/models/bill';
import { BillService } from 'src/app/services/bill/bill.service';
import { DrawerService } from 'src/app/services/drawer/drawer.service';
import { RenewalDialogComponent } from '../../renewal/renewal-dialog/renewal-dialog.component';
import { CloseConfirmationComponent } from '../../shared/controls/close-confirmation/close-confirmation.component';
import { TransactionDialogComponent } from '../../transaction/transaction-dialog/transaction-dialog.component';

@Component({
  selector: 'app-bill-details',
  templateUrl: './bill-details.component.html',
  styleUrls: ['./bill-details.component.scss']
})
export class BillDetailsComponent implements AfterViewInit {

  constructor(
    private billService: BillService,
    private dialog: MatDialog,
    public drawerService: DrawerService
  ) { }

  @ViewChild('drawer') drawer: MatDrawer;
  @Input() bill: Bill = {} as Bill;

  transction(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: this.bill}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(TransactionDialogComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.billService.announceRefreshData();
      }
    });
  }

  renew(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: this.bill}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(RenewalDialogComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.billService.announceRefreshData();
      }
    });
  }

  close(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: this.bill}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(CloseConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.billService.announceRefreshData();
      }
    });
  }

  ngAfterViewInit(): void{
    this.drawerService.register(this.drawer);
  }

}
