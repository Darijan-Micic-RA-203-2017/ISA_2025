import { Routes } from '@angular/router';

import { AccountActivationComponent } from './components/account-activation/account-activation';
import { LoggingOnComponent } from './components/logging-on/logging-on';
import { RegistrationComponent } from './components/registration/registration';

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app<br />
 * https://angular.dev/guide/routing/define-routes
*/
export const routes: Routes = [
	{ path: 'log-on',                       component: LoggingOnComponent         },
	{ path: 'register',                     component: RegistrationComponent      },
	{ path: 'activate-account/:encoded_id', component: AccountActivationComponent }
];
