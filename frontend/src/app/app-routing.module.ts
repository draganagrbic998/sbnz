import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AccountListComponent } from './components/account/account-list/account-list.component';
import { MyAccountComponent } from './components/account/my-account/my-account.component';
import { BaseReportComponent } from './components/common/base-report/base-report.component';
import { BillListComponent } from './components/bill/bill-list/bill-list.component';
import { NotificationListComponent } from './components/notification/notification-list/notification-list.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { ADMIN, KLIJENT, SLUZBENIK } from './utils/constants';
import { AuthGuard } from './utils/auth.guard';

const routes: Routes = [
  {
    path: environment.loginFormRoute,
    component: LoginFormComponent
  },
  {
    path: environment.userListRoute,
    component: UserListComponent,
    canActivate: [AuthGuard],
    data: {roles: [ADMIN]}
  },
  {
    path: environment.accountListRoute,
    component: AccountListComponent,
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
    path: environment.notificationList,
    component: NotificationListComponent,
    canActivate: [AuthGuard],
    data: {roles: [ADMIN, KLIJENT]}
  },
  {
    path: environment.baseReport,
    component: BaseReportComponent,
    canActivate: [AuthGuard],
    data: {roles: [SLUZBENIK]}
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: environment.loginFormRoute
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
