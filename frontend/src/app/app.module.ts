import { NgModule } from '@angular/core';

import { MaterialModule } from './material.module';
import { CommonModule } from './common.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BoldTextComponent } from './components/shared/containers/bold-text/bold-text.component';
import { CenterContainerComponent } from './components/shared/containers/center-container/center-container.component';
import { SpacerContainerComponent } from './components/shared/containers/spacer-container/spacer-container.component';
import { EmptyContainerComponent } from './components/shared/containers/empty-container/empty-container.component';
import { FormContainerComponent } from './components/shared/containers/form-container/form-container.component';
import { RuleResponseComponent } from './components/shared/containers/rule-response/rule-response.component';
import { PreloaderComponent } from './components/shared/loaders/preloader/preloader.component';
import { SpinnerButtonComponent } from './components/shared/loaders/spinner-button/spinner-button.component';
import { PaginatorComponent } from './components/shared/controls/paginator/paginator.component';
import { DeleteConfirmationComponent } from './components/shared/controls/delete-confirmation/delete-confirmation.component';
import { CloseConfirmationComponent } from './components/shared/controls/close-confirmation/close-confirmation.component';
import { NotificationListComponent } from './components/notification/notification-list/notification-list.component';
import { NotificationDetailsComponent } from './components/notification/notification-details/notification-details.component';
import { TransactionDetailsComponent } from './components/transaction/transaction-details/transaction-details.component';
import { TransactionDialogComponent } from './components/transaction/transaction-dialog/transaction-dialog.component';
import { RenewalDetailsComponent } from './components/renewal/renewal-details/renewal-details.component';
import { RenewalDialogComponent } from './components/renewal/renewal-dialog/renewal-dialog.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { UserDialogComponent } from './components/user/user-dialog/user-dialog.component';
import { PasswordDialogComponent } from './components/user/password-dialog/password-dialog.component';
import { BillDetailsComponent } from './components/bill/bill-details/bill-details.component';
import { BillListComponent } from './components/bill/bill-list/bill-list.component';
import { BillFormComponent } from './components/bill/bill-form/bill-form.component';
import { BaseReportComponent } from './components/bill/base-report/base-report.component';
import { AccountDetailsComponent } from './components/account/account-details/account-details.component';
import { AccountListComponent } from './components/account/account-list/account-list.component';
import { AccountFormComponent } from './components/account/account-form/account-form.component';
import { MyAccountComponent } from './components/account/my-account/my-account.component';
import { ToolbarComponent } from './components/shared/controls/toolbar/toolbar.component';

@NgModule({
  declarations: [
    AppComponent,
    BoldTextComponent,
    CenterContainerComponent,
    SpacerContainerComponent,
    EmptyContainerComponent,
    FormContainerComponent,
    RuleResponseComponent,
    PreloaderComponent,
    SpinnerButtonComponent,
    PaginatorComponent,
    DeleteConfirmationComponent,
    CloseConfirmationComponent,
    NotificationListComponent,
    NotificationDetailsComponent,
    TransactionDetailsComponent,
    TransactionDialogComponent,
    RenewalDetailsComponent,
    RenewalDialogComponent,
    LoginFormComponent,
    UserListComponent,
    UserDialogComponent,
    PasswordDialogComponent,
    BillDetailsComponent,
    BillListComponent,
    BillFormComponent,
    BaseReportComponent,
    AccountDetailsComponent,
    AccountListComponent,
    AccountFormComponent,
    MyAccountComponent,
    ToolbarComponent
  ],
  imports: [
    AppRoutingModule,
    CommonModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
