import { Component, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.scss']
})
export class AccountFormComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public account: Account,
    private accountService: AccountService,
    private dialogRef: MatDialogRef<AccountFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  accountForm: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    lastName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    jmbg: new FormControl('', [Validators.required, Validators.minLength(13)]),
    birthDate: new FormControl('', [Validators.required, this.birthDateValidator()]),
    address: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    city: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    zipCode: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S')),
    Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')])
  });

  confirm(): void{
    if (this.accountForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.save({...this.account, ...this.accountForm.value}).subscribe(
      (account: Account) => {
        this.pending = false;
        if (account){
          this.accountService.announceRefreshData();
          this.snackBar.open('Account saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  birthDateValidator(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let dateValid = true;
      if (control.value >= new Date()){
        dateValid = false;
      }
      return dateValid ? null : {dateError: true};
    };
  }

  ngOnInit(): void {
    this.accountForm.reset(this.account);
    this.accountForm.controls.birthDate.reset(new Date(this.account.birthDate));
  }

}
