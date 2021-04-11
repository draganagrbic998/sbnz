import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bill } from 'src/app/models/bill';
import { Pagination } from 'src/app/models/pagination';
import { BillService } from 'src/app/services/bill.service';
import { Page } from 'src/app/models/page';

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
  pending = true;
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
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.billService.findAll(this.route.snapshot.params.type, this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Bill>) => {
        this.pending = false;
        this.bills = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.billService.refreshData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber -= 1 : 0;
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
