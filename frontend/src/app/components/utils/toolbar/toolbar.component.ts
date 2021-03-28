import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PasswordDialogComponent } from 'src/app/components/user/password-dialog/password-dialog.component';
import { UserDialogComponent } from 'src/app/components/user/user-dialog/user-dialog.component';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { AccountService } from 'src/app/services/account/account.service';
import { BillService } from 'src/app/services/bill/bill.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { UserService } from 'src/app/services/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private userService: UserService,
    private accountService: AccountService,
    private billService: BillService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  search: FormControl = new FormControl('');

  get role(): string{
    return this.storageService.getUser().role;
  }

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
    this.storageService.removeUser();
    this.router.navigate([environment.loginRoute]);
  }

  passwordChange(): void{
    this.dialog.open(PasswordDialogComponent, DIALOG_OPTIONS);
  }

  createUser(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: {}}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(UserDialogComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.userService.announceRefreshData();
      }
    });
  }

  createAccount(): void{
    this.storageService.remove(this.storageService.ACCOUNT_KEY);
    this.router.navigate([`${environment.accountFormRoute}/create`]);
  }

  announceSearchData(): void{
    if (this.onRoute('user-list')){
      this.userService.announceSearchData(this.search.value);
    }
    else if (this.onRoute('account-list')){
      this.accountService.announceSearchData(this.search.value);
    }
    else{
      this.billService.announceSearchData(this.search.value);
    }
  }

  report(index: number): void{
    this.accountService.announceReport(index);
  }

  ngOnInit(): void {
  }

}
