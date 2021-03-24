import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { SLUZBENIK } from 'src/app/constants/roles';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.scss']
})
export class UserDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public user: User,
    private userService: UserService,
    public dialogRef: MatDialogRef<UserDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  userForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S')),
    Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')]),
    firstName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    lastName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  confirm(): void{
    if (this.userForm.invalid){
      this.userForm.controls.email.markAsTouched();
      this.userForm.controls.firstName.markAsTouched();
      this.userForm.controls.lastName.markAsTouched();
      return;
    }

    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.userService.save({...this.user, ...this.userForm.value, ...{role: SLUZBENIK}}).subscribe(
      (user: User) => {
        this.savePending = false;
        if (user){
          this.snackBar.open('User saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close(true);
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  ngOnInit(): void {
    this.userForm.reset(this.user);
  }

}
