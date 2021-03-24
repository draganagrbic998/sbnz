import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Account } from 'src/app/models/account';
import { Pagination } from 'src/app/models/pagination';
import { AccountService } from 'src/app/services/account/account.service';

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
  fetchPending = true;
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
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (data: HttpResponse<Account[]>) => {
        this.fetchPending = false;
        if (data){
          this.accounts = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.accounts = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  getReport(index: number): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.getReport(index).subscribe(
      (accounts: Account[]) => {
        this.fetchPending = false;
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
