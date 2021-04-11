import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { BillResponse } from 'src/app/models/bill-response';
import { BillService } from 'src/app/services/bill.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-bill-form',
  templateUrl: './bill-form.component.html',
  styleUrls: ['./bill-form.component.scss']
})
export class BillFormComponent implements OnInit {

  constructor(
    private billService: BillService,
    private dialogRef: MatDialogRef<BillFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  billForm: FormGroup = new FormGroup({
    type: new FormControl('', [Validators.required]),
    months: new FormControl('', [Validators.required]),
    base: new FormControl('', [Validators.required]),
  });

  response: BillResponse;
  pending = false;

  send(): void{
    if (this.billForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.billService.terms(this.billForm.value).subscribe(
      (response: BillResponse) => {
        this.pending = false;
        if (response){
          this.response = response;
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  accept(): void{
    this.pending = false;
    // tslint:disable-next-line: deprecation
    this.billService.create(this.billForm.value).subscribe(
      (response: BillResponse) => {
        this.pending = false;
        if (response){
          this.billService.announceRefreshData();
          this.snackBar.open('Bill saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
