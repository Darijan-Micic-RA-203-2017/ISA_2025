import { Component, inject, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AuthenticationService } from '../../services/authentication/authentication';

import { UserCredentials } from '../../model/user/user-credentials';
import { ParametersOfSubmitUserCredentialsFunction } from '../../utilities/parameters-of-submit-user-credentials-function';
import { numberOfLettersValidator } from '../../validation/number-of-letters/number-of-letters-validator';

@Component({
  standalone: true,
  /* REFERENCES:
   * https://angular.dev/guide/forms
   * https://angular.dev/guide/forms/reactive-forms
   * https://stackoverflow.com/questions/78168666/how-can-i-solve-this-error-usging-routerlink-in-angular-17-2/78168794
  */
  imports: [ReactiveFormsModule, RouterLink, MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule, 
      MatProgressSpinnerModule],
  selector: 'app-logging-on',
  styleUrl: './logging-on.css',
  templateUrl: './logging-on.html'
})
export class LoggingOnComponent {
  loggingOnFormGroup: FormGroup;
  /* REFERENCES:
   * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
   * https://angular.dev/essentials/signals
   * https://angular.dev/guide/signals
  */
  shouldPasswordBeHidden: WritableSignal<boolean>;
  userCredentials: UserCredentials;
  isLoggingOnFormSubmitted: WritableSignal<boolean>;

  // REFERENCE: https://material.angular.dev/components/snack-bar/overview
  snackBar: MatSnackBar = inject(MatSnackBar);

  parametersOfSubmitUserCredentialsFunction: ParametersOfSubmitUserCredentialsFunction;

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, public router: Router) {
    this.loggingOnFormGroup = this.formBuilder.group({
      usernameControl: new FormControl<string>('', {
        /* REFERENCES:
         * https://angular.dev/guide/forms/form-validation
         * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions
         * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
         * https://angular.dev/guide/forms/form-validation
         * https://blog.angular-university.io/angular-custom-validators/
        */
        validators: [Validators.required, Validators.pattern(/^[A-Za-z0-9~!@#\$%\^&\*\(\)\-_=\+\[\]\|:<>\.]+$/),
            numberOfLettersValidator(4), Validators.minLength(8), Validators.maxLength(32)],
        updateOn: 'change'
      }),
      passwordControl: new FormControl<string>('', {
        validators: [Validators.required, Validators.pattern(/^\S+$/),
            Validators.minLength(8), Validators.maxLength(16)],
        updateOn: 'change'
      })
    });
    this.shouldPasswordBeHidden = signal<boolean>(true);
    this.userCredentials = new UserCredentials(null);
    this.isLoggingOnFormSubmitted = signal<boolean>(false);

    this.parametersOfSubmitUserCredentialsFunction = new ParametersOfSubmitUserCredentialsFunction(this.userCredentials, 
        this.isLoggingOnFormSubmitted, this.snackBar, router);
  }

  /** REFERENCE: https://material.angular.dev/components/form-field/examples */
  changeVisibilityOfPassword(event: MouseEvent): void {
    this.shouldPasswordBeHidden.set(!this.shouldPasswordBeHidden());

    event.stopPropagation();
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  submitUserCredentials(parameters: ParametersOfSubmitUserCredentialsFunction): void {
    this.parametersOfSubmitUserCredentialsFunction.setIsLoggingOnFormSubmitted(true);

    /* REFERENCES:<br />
     * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
     * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
    */
    this.parametersOfSubmitUserCredentialsFunction.getUserCredentials()
        .setUsername(this.loggingOnFormGroup.value['usernameControl']);
    this.parametersOfSubmitUserCredentialsFunction.getUserCredentials()
        .setPassword(this.loggingOnFormGroup.value['passwordControl']);

    this.authenticationService.logOnWith(parameters);
  }
}
