import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { RuleResponse } from 'src/app/models/rule-response';
import { BillService } from 'src/app/services/bill.service';

@Component({
  selector: 'app-transaction-form',
  templateUrl: './transaction-form.component.html',
  styleUrls: ['./transaction-form.component.scss']
})
export class TransactionFormComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private billId: number,
    private billService: BillService,
    private dialogRef: MatDialogRef<TransactionFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  amount: FormControl = new FormControl('', [Validators.required]);
  pending = false;
  response: RuleResponse;

  confirm(): void{
    if (this.amount.invalid){
      return;
    }
    this.response = null;
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.billService.transaction(this.billId, this.amount.value).subscribe(
      (response: RuleResponse) => {
        this.pending = false;
        if (!response){
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
        else if (response.valid){
          this.billService.announceRefreshData();
          this.snackBar.open('Bill increased!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.response = response;
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
