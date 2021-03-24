// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  authApi: 'http://localhost:8080/auth',
  usersApi: 'http://localhost:8080/users',
  accountsApi: 'http://localhost:8080/accounts',
  billsApi: 'http://localhost:8080/bills',
  notificationsApi: 'http://localhost:8080/notifications',

  loginRoute: 'login-form',
  userListRoute: 'user-list',
  accountListRoute: 'account-list',
  accountFormRoute: 'account-form',
  myAccountRoute: 'my-account',
  billListRoute: 'bill-list',
  billFormRoute: 'bill-form',
  notificationList: 'notification-list',
  baseReport: 'base-report'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
