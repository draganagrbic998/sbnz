import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Pagination } from 'src/app/models/pagination';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { DeleteConfirmationComponent } from '../../common/delete-confirmation/delete-confirmation.component';
import { UserFormComponent } from '../user-form/user-form.component';
import { Page } from 'src/app/models/page';
import { DeleteData } from 'src/app/models/delete-data';

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
  pending = true;
  pagination: Pagination = {
    firstPage: true,
    lastPage: true,
    pageNumber: 0
  };
  search = '';

  edit(user: User): void{
    this.dialog.open(UserFormComponent, {...DIALOG_OPTIONS, ...{data: user}});
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.userService.delete(id),
      refreshFunction: () => this.userService.announceRefreshData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchUsers();
  }

  fetchUsers(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.userService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<User>) => {
        this.pending = false;
        this.users = new MatTableDataSource(page.content);
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
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
