import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

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
  isLoggingOnFormSubmitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {
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
  }

  submitUserCredentials(): void {}
}
