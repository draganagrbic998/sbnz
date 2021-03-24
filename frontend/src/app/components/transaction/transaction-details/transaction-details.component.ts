import { Component, Input, OnInit } from '@angular/core';
import { Transaction } from 'src/app/models/transaction';

@Component({
  selector: 'app-transaction-details',
  templateUrl: './transaction-details.component.html',
  styleUrls: ['./transaction-details.component.scss']
})
export class TransactionDetailsComponent implements OnInit {

  constructor() { }

  @Input() transaction: Transaction = {} as Transaction;
  @Input() index = 0;
  @Input() currency = 'RSD';

  ngOnInit(): void {
  }

}
