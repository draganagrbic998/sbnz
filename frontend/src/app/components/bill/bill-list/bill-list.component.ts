import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Bill } from 'src/app/models/bill';
import { Pagination } from 'src/app/models/pagination';
import { BillService } from 'src/app/services/bill/bill.service';

@Component({
  selector: 'app-bill-list',
  templateUrl: './bill-list.component.html',
  styleUrls: ['./bill-list.component.scss']
})
export class BillListComponent implements OnInit {

  constructor(
    private billService: BillService,
    private route: ActivatedRoute
  ) { }

  bills: Bill[] = [];
  fetchPending = true;
  pagination: Pagination = {
    firstPage: true,
    lastPage: true,
    pageNumber: 0
  };
  search = '';

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchBills();
  }

  fetchBills(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.billService.findAll(this.route.snapshot.params.type, this.pagination.pageNumber, this.search).subscribe(
      (data: HttpResponse<Bill[]>) => {
        this.fetchPending = false;
        if (data){
          this.bills = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.bills = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.billService.refreshData$.subscribe(() => {
      this.changePage(0);
    });
    // tslint:disable-next-line: deprecation
    this.billService.searchData$.subscribe((search: string) => {
      this.pagination.pageNumber = 0;
      this.search = search;
      this.changePage(0);
    });
  }

}
