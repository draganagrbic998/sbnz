import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { RuleResponse } from 'src/app/models/rule-response';
import { BillService } from 'src/app/services/bill.service';

@Component({
  selector: 'app-close-confirmation',
  templateUrl: './close-confirmation.component.html',
  styleUrls: ['./close-confirmation.component.scss']
})
export class CloseConfirmationComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private billId: number,
    private billService: BillService,
    private dialogRef: MatDialogRef<CloseConfirmationComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  response: RuleResponse;

  confirm(): void{
    this.response = null;
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.billService.delete(this.billId).subscribe(
      (response: RuleResponse) => {
        this.pending = false;
        if (!response){
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
        else if (response.valid){
          this.billService.announceRefreshData();
          this.snackBar.open('Bill closed!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
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
