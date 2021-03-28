import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Bill } from 'src/app/models/bill';
import { RuleResponse } from 'src/app/models/rule-response';
import { BillService } from 'src/app/services/bill/bill.service';

@Component({
  selector: 'app-close-confirmation',
  templateUrl: './close-confirmation.component.html',
  styleUrls: ['./close-confirmation.component.scss']
})
export class CloseConfirmationComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private bill: Bill,
    private billService: BillService,
    public dialogRef: MatDialogRef<CloseConfirmationComponent>,
    private snackBar: MatSnackBar
  ) { }

  closePending = false;
  response: RuleResponse;

  confirm(): void{
    this.response = null;
    this.closePending = true;
    // tslint:disable-next-line: deprecation
    this.billService.delete(this.bill.id).subscribe(
      (response: RuleResponse) => {
        this.closePending = false;
        if (!response){
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
        else if (response.valid){
          this.snackBar.open('Bill closed!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
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
