import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { FIRST_PAGE, LAST_PAGE } from 'src/app/constants/pagination';
import { Pagination } from 'src/app/models/pagination';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user/user.service';
import { DeleteConfirmationComponent } from '../../utils/delete-confirmation/delete-confirmation.component';
import { UserDialogComponent } from '../user-dialog/user-dialog.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  constructor(
    private userService: UserService,
    private dialog: MatDialog
  ) { }

  columns: string[] = ['role', 'email', 'firstName', 'lastName', 'actions'];
  users: MatTableDataSource<User> = new MatTableDataSource([]);
  fetchPending = true;
  pagination: Pagination = {
    firstPage: true,
    lastPage: true,
    pageNumber: 0
  };
  search = '';

  edit(user: User): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: user}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(UserDialogComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.userService.announceRefreshData();
      }
    });
  }

  delete(id: number): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.userService.delete(id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.userService.announceRefreshData();
      }
    });
  }

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchUsers();
  }

  fetchUsers(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.userService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (data: HttpResponse<User[]>) => {
        this.fetchPending = false;
        if (data){
          this.users = new MatTableDataSource(data.body);
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE) === 'false' ? false : true;
        }
        else{
          this.users = new MatTableDataSource([]);
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.userService.refreshData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber -= 1 : 0;
      this.changePage(0);
    });
    // tslint:disable-next-line: deprecation
    this.userService.searchData$.subscribe((search: string) => {
      this.pagination.pageNumber = 0;
      this.search = search;
      this.changePage(0);
    });
  }

}
