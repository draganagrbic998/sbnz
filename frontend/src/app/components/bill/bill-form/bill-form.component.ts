import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { BillRequest } from 'src/app/models/bill-request';
import { BillResponse } from 'src/app/models/bill-response';
import { BillService } from 'src/app/services/bill/bill.service';

@Component({
  selector: 'app-bill-form',
  templateUrl: './bill-form.component.html',
  styleUrls: ['./bill-form.component.scss']
})
export class BillFormComponent implements OnInit {

  constructor(
    private billService: BillService,
    private snackBar: MatSnackBar,
    public location: Location
  ) { }

  billForm: FormGroup = new FormGroup({
    type: new FormControl('', [Validators.required]),
    months: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]),
    base: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]),
  });

  request: BillRequest;
  response: BillResponse;
  responsePending = false;
  acceptPending = false;

  send(): void{
    if (this.billForm.invalid){
      return;
    }
    this.request = this.billForm.value;
    this.responsePending = true;
    // tslint:disable-next-line: deprecation
    this.billService.terms(this.request).subscribe(
      (response: BillResponse) => {
        this.responsePending = false;
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
    this.acceptPending = false;
    // tslint:disable-next-line: deprecation
    this.billService.create(this.request).subscribe(
      (response: BillResponse) => {
        this.acceptPending = false;
        if (response){
          this.snackBar.open('Bill saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.reset();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  reset(): void{
    this.billForm.reset();
    this.billForm.clearValidators();
    this.request = null;
    this.response = null;
  }

  ngOnInit(): void {
  }

}
