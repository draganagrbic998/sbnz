import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatDrawer } from '@angular/material/sidenav';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Bill } from 'src/app/models/bill';
import { DrawerService } from 'src/app/services/drawer.service';
import { RenewalFormComponent } from '../../renewal/renewal-form/renewal-form.component';
import { CloseConfirmationComponent } from '../close-confirmation/close-confirmation.component';
import { TransactionFormComponent } from '../../transaction/transaction-form/transaction-form.component';

@Component({
  selector: 'app-bill-details',
  templateUrl: './bill-details.component.html',
  styleUrls: ['./bill-details.component.scss']
})
export class BillDetailsComponent implements AfterViewInit {

  constructor(
    private dialog: MatDialog,
    public drawerService: DrawerService
  ) { }

  @ViewChild('drawer') drawer: MatDrawer;
  @Input() bill: Bill = {} as Bill;

  transction(): void{
    this.dialog.open(TransactionFormComponent, {...DIALOG_OPTIONS, ...{data: this.bill.id}});
  }

  renewal(): void{
    this.dialog.open(RenewalFormComponent, {...DIALOG_OPTIONS, ...{data: this.bill.id}});
  }

  close(): void{
    this.dialog.open(CloseConfirmationComponent, {...DIALOG_OPTIONS, ...{data: this.bill.id}});
  }

  ngAfterViewInit(): void{
    this.drawerService.register(this.drawer);
  }

}
