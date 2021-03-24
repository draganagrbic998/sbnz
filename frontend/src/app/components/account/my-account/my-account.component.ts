import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  constructor(
    private accountService: AccountService,
    private router: Router
  ) { }

  account: Account;
  fetchPending = true;

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.accountService.myAccount().subscribe(
      (account: Account) => {
        this.fetchPending = false;
        if (account){
          this.account = account;
        }
        else{
          this.router.navigate([environment.loginRoute]);
        }
      }
    );
  }

}
