import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  constructor(
    private accountService: AccountService
  ) { }

  account: Account;
  fetchPending = true;

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.accountService.myAccount().subscribe(
      (account: Account) => {
        this.fetchPending = false;
        this.account = account;
      }
    );
  }

}
