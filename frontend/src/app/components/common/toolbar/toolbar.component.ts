import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PasswordFormComponent } from 'src/app/components/user/password-form/password-form.component';
import { UserFormComponent } from 'src/app/components/user/user-form/user-form.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { AccountService } from 'src/app/services/account.service';
import { BillService } from 'src/app/services/bill.service';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { AccountFormComponent } from '../../account/account-form/account-form.component';
import { BillFormComponent } from '../../bill/bill-form/bill-form.component';

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
    return this.storageService.getUser()?.role;
  }

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
    this.storageService.removeUser();
    this.router.navigate([environment.loginFormRoute]);
  }

  openPasswordDialog(): void{
    this.dialog.open(PasswordFormComponent, DIALOG_OPTIONS);
  }

  openUserDialog(): void{
    this.dialog.open(UserFormComponent, {...DIALOG_OPTIONS, ...{data: {}}});
  }

  openAccountDialog(): void{
    this.dialog.open(AccountFormComponent, {...DIALOG_OPTIONS, ...{data: {}}});
  }

  openBillDialog(): void{
    this.dialog.open(BillFormComponent, DIALOG_OPTIONS);
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

  announceReport(index: number): void{
    this.accountService.announceReport(index);
  }

  ngOnInit(): void {
  }

}
