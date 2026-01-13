import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AuthenticationService } from '../../services/authentication/authentication';

import { ObjectWithTextualContext } from '../../model/object-with-textual-context';
import { TokenWithLifeDuration } from '../../model/user/token-with-life-duration';
import { UserCredentials } from '../../model/user/user-credentials';

@Component({
  standalone: true,
  /* REFERENCES:
  * https://angular.dev/guide/forms
  * https://angular.dev/guide/forms/reactive-forms
  */
  imports: [ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule],
  selector: 'app-logging-on',
  styleUrl: './logging-on.css',
  templateUrl: './logging-on.html'
})
export class LoggingOnComponent {
  loggingOnFormGroup: FormGroup;
  isLoggingOnFormSubmitted: boolean;

  // REFERENCE: https://material.angular.dev/components/snack-bar/overview
  snackBar: MatSnackBar = inject(MatSnackBar);

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService) {
    this.loggingOnFormGroup = this.formBuilder.group({
      usernameControl: new FormControl<string | null>('', {
        /* REFERENCES:
        * https://angular.dev/guide/forms/form-validation
        * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions
        * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
        */
        validators: [Validators.required, Validators.pattern(/^[A-Za-z0-9~!@#\$%\^&\*\(\)\-_=\+\[\]\|:<>\.]+$/),
            Validators.pattern(/([^A-Za-z]?[A-Za-z][^A-Za-z]?){4,}/), Validators.minLength(8), Validators.maxLength(32)],
        updateOn: 'change'
      }),
      passwordControl: new FormControl<string | null>('', {
        validators: [Validators.required, Validators.pattern(/^\S+$/),
            Validators.minLength(8), Validators.maxLength(16)],
        updateOn: 'change'
      })
    });
    this.isLoggingOnFormSubmitted = false;
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  submitUserCredentials(isFormSubmitted: boolean, snackBar: MatSnackBar): void {
    isFormSubmitted = true;

    /* REFERENCES:<br />
     * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
     * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
    */
    let userCredentials: UserCredentials = new UserCredentials({
      'username': this.loggingOnFormGroup.value['usernameControl'],
      'password': this.loggingOnFormGroup.value['passwordControl']
    });

    // REFERENCE: https://rxjs.dev/deprecations/subscribe-arguments
    this.authenticationService.logOnWith(userCredentials).subscribe({
      next(responseObject: ObjectWithTextualContext): void {
        /* REFERENCES:<br />
         * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
         * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
        */
        let objectWithTextualContext: ObjectWithTextualContext = new ObjectWithTextualContext(responseObject);
        console.log('Logging on response:', objectWithTextualContext);

        let tokenWithLifeDuration: TokenWithLifeDuration = new TokenWithLifeDuration(objectWithTextualContext.getObject());
        let token: string | null = tokenWithLifeDuration.getToken();
        localStorage.setItem('jwtToken', token);

        snackBar.open('Успешно сте пријављени на систем Јутјубића.', 'Затворите', { duration: 5000 });
      },
      error(errorResponse: HttpErrorResponse): void {
        isFormSubmitted = false;

        let error: ObjectWithTextualContext = new ObjectWithTextualContext(errorResponse.error);
        console.log(`Error while logging in!\n\n${error.getTextualContext()}`);
        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        if (errorResponse.status == 406) {
          snackBar.open('Кориснички налог је онемогућен!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 423) {
          snackBar.open('Кориснички налог је закључан!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 400) {
          snackBar.open('Унето је неисправно корисничко име и/или лозинка!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 422) {
          snackBar.open('Дошло је до унутрашње аутентификационе грешке! Молимо Вас, покушајте поново касније.',
              'Затворите', { duration: 5000 });
        }
      }
    });
  }
}
