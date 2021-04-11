import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-password-form',
  templateUrl: './password-form.component.html',
  styleUrls: ['./password-form.component.scss']
})
export class PasswordFormComponent implements OnInit {

  constructor(
    private userService: UserService,
    private dialogRef: MatDialogRef<PasswordFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  passwordForm: FormGroup = new FormGroup({
    oldPassword: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    newPassword: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    newPasswordConfirmation: new FormControl('', [this.passwordConfirmed()])
  });

  confirm(): void{
    if (this.passwordForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.userService.changePassword(this.passwordForm.value).subscribe(
      (response: boolean) => {
        this.pending = false;
        if (response){
          this.snackBar.open('Password changed!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
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
