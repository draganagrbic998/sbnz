import { Component, OnInit } from '@angular/core';
import { BaseReport } from 'src/app/models/base-report';
import { BillService } from 'src/app/services/bill.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  constructor(
    private billService: BillService
  ) { }

  pending = true;
  report: BaseReport;

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.billService.getReport().subscribe(
      (report: BaseReport) => {
        this.pending = false;
        this.report = report;
      }
    );
  }

}
