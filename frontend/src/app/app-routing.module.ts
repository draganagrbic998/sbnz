import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AccountFormComponent } from './components/account/account-form/account-form.component';
import { AccountListComponent } from './components/account/account-list/account-list.component';
import { MyAccountComponent } from './components/account/my-account/my-account.component';
import { BaseReportComponent } from './components/bill/base-report/base-report.component';
import { BillFormComponent } from './components/bill/bill-form/bill-form.component';
import { BillListComponent } from './components/bill/bill-list/bill-list.component';
import { NotificationListComponent } from './components/notification/notification-list/notification-list.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { ADMIN, KLIJENT, SLUZBENIK } from './constants/roles';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  {
    path: environment.loginRoute,
    component: LoginFormComponent
  },
  {
    path: environment.accountListRoute,
    component: AccountListComponent,
    canActivate: [AuthGuard],
    data: {roles: [SLUZBENIK]}
  },
  {
    path: `${environment.accountFormRoute}/:mode`,
    component: AccountFormComponent,
    canActivate: [AuthGuard],
    data: {roles: [SLUZBENIK]}
  },
  {
    path: environment.myAccountRoute,
    component: MyAccountComponent,
    canActivate: [AuthGuard],
    data: {roles: [KLIJENT]}
  },
  {
    path: `${environment.billListRoute}/:type`,
    component: BillListComponent,
    canActivate: [AuthGuard],
    data: {roles: [KLIJENT]}
  },
  {
    path: environment.billFormRoute,
    component: BillFormComponent,
    canActivate: [AuthGuard],
    data: {roles: [KLIJENT]}
  },
  {
    path: environment.baseReport,
    component: BaseReportComponent,
    canActivate: [AuthGuard],
    data: {roles: [SLUZBENIK]}
  },
  {
    path: environment.userListRoute,
    component: UserListComponent,
    canActivate: [AuthGuard],
    data: {roles: [ADMIN]}
  },
  {
    path: environment.notificationList,
    component: NotificationListComponent,
    canActivate: [AuthGuard],
    data: {roles: [KLIJENT]}
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: environment.loginRoute
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
