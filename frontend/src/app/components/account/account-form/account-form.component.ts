import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.scss']
})
export class AccountFormComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private accountService: AccountService,
    private snackBar: MatSnackBar,
    public location: Location,
    private route: ActivatedRoute
  ) { }

  account: Account = {} as Account;
  savePending = false;
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

  save(): void{
    if (this.accountForm.invalid){
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.accountService.save({...this.account, ...this.accountForm.value}).subscribe(
      (account: Account) => {
        this.savePending = false;
        if (account){
          this.snackBar.open('Account saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          if (!this.account.id){
            this.accountForm.reset();
            this.accountForm.clearValidators();
          }
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
    if (this.route.snapshot.params.mode === 'edit'){
      this.account = this.storageService.get(this.storageService.ACCOUNT_KEY) as Account;
      this.accountForm.reset(this.account);
      this.accountForm.controls.birthDate.reset(new Date(this.account.birthDate));
    }
  }

}
