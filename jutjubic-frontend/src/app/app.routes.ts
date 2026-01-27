import { Routes } from '@angular/router';

import { LoggingOnComponent } from './components/logging-on/logging-on';
import { RegistrationComponent } from './components/registration/registration';

/** REFERENCES:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app
 * https://angular.dev/guide/routing/define-routes
*/
export const routes: Routes = [
	{ path: 'log-on',   component: LoggingOnComponent    },
	{ path: 'register', component: RegistrationComponent }
];
