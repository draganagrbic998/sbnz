import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { Pagination } from 'src/app/models/pagination';
import { AccountService } from 'src/app/services/account.service';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.scss']
})
export class AccountListComponent implements OnInit {

  constructor(
    private accountService: AccountService
  ) { }

  accounts: Account[] = [];
  pending = true;
  pagination: Pagination = {
    firstPage: true,
    lastPage: true,
    pageNumber: 0
  };
  search = '';

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchAccounts();
  }

  fetchAccounts(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Account>) => {
        this.pending = false;
        this.accounts = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  getReport(index: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.getReport(index).subscribe(
      (accounts: Account[]) => {
        this.pending = false;
        this.accounts = accounts;
        this.pagination.firstPage = true;
        this.pagination.lastPage = true;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.accountService.refreshData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber -= 1 : 0;
      this.changePage(0);
    });
    // tslint:disable-next-line: deprecation
    this.accountService.searchData$.subscribe((search: string) => {
      this.pagination.pageNumber = 0;
      this.search = search;
      this.changePage(0);
    });
    // tslint:disable-next-line: deprecation
    this.accountService.report$.subscribe((index: number) => this.getReport(index));
  }

}
