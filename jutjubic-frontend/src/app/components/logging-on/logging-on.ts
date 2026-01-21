import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';

import { JwtPayload, jwtDecode } from 'jwt-decode';

import { AuthenticationService } from '../../services/authentication/authentication';

import { ObjectWithTextualContext } from '../../model/object-with-textual-context';
import { TokenWithLifeDuration } from '../../model/user/token-with-life-duration';
import { UserCredentials } from '../../model/user/user-credentials';
import { ParametersOfSubmitUserCredentialsFunction } from '../../utilities/parameters-of-submit-user-credentials-function';

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
  userCredentials: UserCredentials;
  /** REFERENCES:
   * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
   * https://angular.dev/essentials/signals
   * https://angular.dev/guide/signals
  */
  isLoggingOnFormSubmitted: WritableSignal<boolean>;

  // REFERENCE: https://material.angular.dev/components/snack-bar/overview
  snackBar: MatSnackBar = inject(MatSnackBar);

  parametersOfSubmitUserCredentialsFunction: ParametersOfSubmitUserCredentialsFunction;

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, public router: Router) {
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
    this.userCredentials = new UserCredentials(null);
    this.isLoggingOnFormSubmitted = signal<boolean>(false);

    this.parametersOfSubmitUserCredentialsFunction = new ParametersOfSubmitUserCredentialsFunction(this.userCredentials, 
        this.isLoggingOnFormSubmitted, this.snackBar, router);
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  submitUserCredentials(parametersOfSubmitUserCredentialsFunction: ParametersOfSubmitUserCredentialsFunction): void {
    parametersOfSubmitUserCredentialsFunction.setIsLoggingOnFormSubmitted(true);

    /* REFERENCES:<br />
     * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
     * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
    */
    this.parametersOfSubmitUserCredentialsFunction.getUserCredentials()
        .setUsername(this.loggingOnFormGroup.value['usernameControl']);
    this.parametersOfSubmitUserCredentialsFunction.getUserCredentials()
        .setPassword(this.loggingOnFormGroup.value['passwordControl']);

    // REFERENCE: https://rxjs.dev/deprecations/subscribe-arguments
    this.authenticationService.logOnWith(this.userCredentials).subscribe({
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
        /* REFERENCES:
         * https://stackoverflow.com/questions/48075688/how-to-decode-the-jwt-encoded-token-payload-on-client-side-in-angular
         * https://github.com/auth0/jwt-decode
        */
        let decodedToken: JwtPayload = jwtDecode<JwtPayload>(token);
        let subjectOfToken: string | undefined = decodedToken.sub;
        if (subjectOfToken) {
          localStorage.setItem('username', subjectOfToken.toString());
        }
        let lifeDurationOfTokenInMilliseconds: number | undefined = decodedToken.exp;
        if (lifeDurationOfTokenInMilliseconds) {
          localStorage.setItem('exp', lifeDurationOfTokenInMilliseconds.toString());
        } else {
          localStorage.setItem('exp', tokenWithLifeDuration.getLifeDurationOfTokenInMilliseconds().toString());
        }

        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        parametersOfSubmitUserCredentialsFunction.getSnackBar().open('Успешно сте пријављени на систем Јутјубића.', 
            'Затворите', { duration: 5000 });

        parametersOfSubmitUserCredentialsFunction.getRouter().navigateByUrl('/').then(() => { window.location.reload(); });
      },
      error(errorResponse: HttpErrorResponse): void {
        parametersOfSubmitUserCredentialsFunction.setIsLoggingOnFormSubmitted(false);

        let error: ObjectWithTextualContext = new ObjectWithTextualContext(errorResponse.error);
        console.log(`Error while logging in!\n\n${error.getTextualContext()}`);
        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        if (errorResponse.status == 406) {
          parametersOfSubmitUserCredentialsFunction.getSnackBar().open('Кориснички налог је онемогућен!', 
              'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 423) {
          parametersOfSubmitUserCredentialsFunction.getSnackBar().open('Кориснички налог је закључан!', 
              'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 400) {
          parametersOfSubmitUserCredentialsFunction.getSnackBar().open('Унето је неисправно корисничко име и/или лозинка!', 
              'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 422) {
          parametersOfSubmitUserCredentialsFunction.getSnackBar().open(
              'Дошло је до унутрашње аутентификационе грешке! Молимо Вас, покушајте поново касније.', 
              'Затворите', { duration: 5000 });
        }
      }
    });
  }
}
