import { Component, OnInit } from '@angular/core';
import { BaseReport } from 'src/app/models/base-report';
import { BillService } from 'src/app/services/bill.service';

@Component({
  selector: 'app-base-report',
  templateUrl: './base-report.component.html',
  styleUrls: ['./base-report.component.scss']
})
export class BaseReportComponent implements OnInit {

  constructor(
    private billService: BillService
  ) { }

  report: BaseReport;
  pending = true;

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.billService.baseReport().subscribe(
      (report: BaseReport) => {
        this.pending = false;
        this.report = report;
      }
    );
  }

}