import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { environment } from 'src/environments/environment';
import { DeleteConfirmationComponent } from '../../shared/controls/delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.scss']
})
export class AccountDetailsComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private accountService: AccountService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  @Input() account: Account = {} as Account;
  @Input() big: boolean;

  get myAccount(): boolean{
    return this.router.url.substr(1) === 'my-account';
  }

  edit(): void{
    this.storageService.set(this.storageService.ACCOUNT_KEY, this.account);
    this.router.navigate([`${environment.accountFormRoute}/edit`]);
  }

  delete(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.accountService.delete(this.account.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.accountService.announceRefreshData();
      }
    });
  }

  ngOnInit(): void {
  }

}
