import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Bill } from 'src/app/models/bill';
import { RuleResponse } from 'src/app/models/rule-response';
import { BillService } from 'src/app/services/bill/bill.service';

@Component({
  selector: 'app-renewal-dialog',
  templateUrl: './renewal-dialog.component.html',
  styleUrls: ['./renewal-dialog.component.scss']
})
export class RenewalDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private bill: Bill,
    private billService: BillService,
    public dialogRef: MatDialogRef<RenewalDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  response: RuleResponse;
  amount: FormControl = new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]);

  confirm(): void{
    if (this.amount.invalid){
      this.amount.markAsTouched();
      return;
    }
    this.response = null;
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.billService.renew(this.bill.id, this.amount.value).subscribe(
      (response: RuleResponse) => {
        this.savePending = false;
        if (!response){
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
        else if (response.valid){
          this.snackBar.open('Bill renewed!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close(true);
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
