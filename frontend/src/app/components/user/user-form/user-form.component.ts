import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { SLUZBENIK } from 'src/app/utils/constants';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public user: User,
    private userService: UserService,
    private dialogRef: MatDialogRef<UserFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  userForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S')),
    Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')]),
    firstName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    lastName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  confirm(): void{
    if (this.userForm.invalid){
      return;
    }

    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.userService.save({...this.user, ...this.userForm.value, ...{role: SLUZBENIK}}).subscribe(
      (user: User) => {
        this.pending = false;
        if (user){
          this.userService.announceRefreshData();
          this.snackBar.open('User saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
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
