import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-password-dialog',
  templateUrl: './password-dialog.component.html',
  styleUrls: ['./password-dialog.component.scss']
})
export class PasswordDialogComponent implements OnInit {

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<PasswordDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  passwordForm: FormGroup = new FormGroup({
    oldPassword: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    newPassword: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    newPasswordConfirmation: new FormControl('', [this.passwordConfirmed()])
  });

  confirm(): void{
    if (this.passwordForm.invalid){
      this.passwordForm.controls.oldPassword.markAsTouched();
      this.passwordForm.controls.newPassword.markAsTouched();
      this.passwordForm.controls.newPasswordConfirmation.markAsTouched();
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.userService.changePassword(this.passwordForm.value).subscribe(
      (response: boolean) => {
        this.savePending = false;
        if (response){
          this.snackBar.open('Password changed!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close(true);
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  private passwordConfirmed(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      const passwordConfirmed: boolean = control.parent ?
      control.value === control.parent.get('newPassword').value : true;
      return passwordConfirmed ? null : {passwordError: true};
    };
  }

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.passwordForm.controls.newPassword.valueChanges.subscribe(
      () => {
        this.passwordForm.controls.newPasswordConfirmation.updateValueAndValidity();
      }
    );
  }

}
