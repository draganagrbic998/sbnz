import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account.service';
import { DeleteConfirmationComponent } from '../../common/delete-confirmation/delete-confirmation.component';
import { DeleteData } from 'src/app/models/delete-data';
import { AccountFormComponent } from '../account-form/account-form.component';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.scss']
})
export class AccountDetailsComponent implements OnInit {

  constructor(
    private accountService: AccountService,
    private dialog: MatDialog
  ) { }

  @Input() account: Account = {} as Account;
  @Input() myAccount: boolean;

  edit(): void{
    this.dialog.open(AccountFormComponent, {...DIALOG_OPTIONS, ...{data: this.account}});
  }

  delete(): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.accountService.delete(this.account.id),
      refreshFunction: () => this.accountService.announceRefreshData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
  }

}
