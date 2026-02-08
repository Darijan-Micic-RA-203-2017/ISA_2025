import { Component, inject, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';

import { RegistrationService } from '../../services/registration/registration';

import { Address } from '../../model/address/address';
import { UserRegistrationRequest } from '../../model/user/user-registration-request';
import { ParametersOfSubmitUserRegistrationRequestFunction } from '../../utilities/parameters-of-submit-user-registration-request-function';
import { numberOfLettersValidator } from '../../validation/number-of-letters/number-of-letters-validator';
import { repeatedPasswordValidator } from '../../validation/repeated-password/repeated-password-validator';

@Component({
  standalone: true,
  /* REFERENCES:
   * https://angular.dev/guide/forms
   * https://angular.dev/guide/forms/reactive-forms
   * https://stackoverflow.com/questions/78168666/how-can-i-solve-this-error-usging-routerlink-in-angular-17-2/78168794
  */
  imports: [ReactiveFormsModule, RouterLink, MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule, 
      MatProgressSpinnerModule],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class RegistrationComponent {
  registrationFormGroup: FormGroup<any>;
  /* REFERENCES:
   * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
   * https://angular.dev/essentials/signals
   * https://angular.dev/guide/signals
  */
  shouldPasswordBeHidden: WritableSignal<boolean>;
  shouldRepeatedPasswordBeHidden: WritableSignal<boolean>;
  userRegistrationRequest: UserRegistrationRequest;
  isRegistrationFormSubmitted: WritableSignal<boolean>;
  hasEmailMessageForAccountActivationBeenSent: WritableSignal<boolean>;

  // REFERENCE: https://material.angular.dev/components/snack-bar/overview
  snackBar: MatSnackBar = inject<MatSnackBar>(MatSnackBar);

  parametersOfSubmitUserRegistrationRequestFunction: ParametersOfSubmitUserRegistrationRequestFunction;

  constructor(private formBuilder: FormBuilder, private registrationService: RegistrationService) {
    this.registrationFormGroup = this.formBuilder.group({
      emailAddressControl: new FormControl<string>('', {
        /* REFERENCES:
         * https://angular.dev/guide/forms/form-validation
        */
        validators: [Validators.required, Validators.email],
        updateOn: 'change'
      }),
      usernameControl: new FormControl<string>('', {
        /* REFERENCES:
         * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions
         * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
         * https://blog.angular-university.io/angular-custom-validators/
        */
        validators: [Validators.required, Validators.pattern(/^[A-Za-z0-9~!@#\$%\^&\*\(\)\-_=\+\[\]\|:<>\.]+$/),
            numberOfLettersValidator(4), Validators.minLength(8), Validators.maxLength(32)],
        updateOn: 'change'
      }),
      passwordControl: new FormControl<string>('', {
        validators: [Validators.required, Validators.pattern(/^\S+$/), repeatedPasswordValidator(),
            Validators.minLength(8), Validators.maxLength(16)],
        updateOn: 'change'
      }),
      repeatedPasswordControl: new FormControl<string>('', {
        validators: [Validators.required, Validators.pattern(/^\S+$/), repeatedPasswordValidator(),
            Validators.minLength(8), Validators.maxLength(16)],
        updateOn: 'change'
      }),
      firstNameControl: new FormControl<string>('', {
        // REFERENCE: https://forum.knime.com/t/string-manipulation-multi-column-regex-patternsyntaxexception-illegal-repetition/60894/4
        validators: [Validators.required, Validators.pattern(/^\p{Lu}('\p{Lu})?\p{Ll}+([ \-]\p{Lu}('\p{Lu})?\p{Ll}+)?$/u)],
        updateOn: 'change'
      }),
      lastNameControl: new FormControl<string>('', {
        validators: [Validators.required, Validators.pattern(/^\p{L}('\p{Lu})?\p{Ll}+([ \-]\p{L}('\p{Lu})?\p{Ll}+){0,2}$/u)],
        updateOn: 'change'
      }),
      addressFormGroup: this.formBuilder.group({
        streetControl: new FormControl<string | null>(null, {
          validators: Validators.pattern(/^$|\S/u),
          updateOn: 'change'
        }),
        numberControl: new FormControl<string | null>(null, {
          validators: Validators.pattern(/^$|\S/u),
          updateOn: 'change'
        }),
        postalCodeControl: new FormControl<string | null>(null, {
          validators: Validators.pattern(/^$|\S/u),
          updateOn: 'change'
        }),
        placeControl: new FormControl<string>('', {
          validators: [Validators.required, Validators.pattern(/^$|\S/u)],
          updateOn: 'change'
        }),
        countryControl: new FormControl<string>('', {
          validators: [Validators.required, Validators.pattern(/^\p{Lu}\p{Ll}+( \p{Lu}\p{Ll}+)?$/u)],
          updateOn: 'change'
        }),
        latitudeControl: new FormControl<number>(0.0, {
          validators: [Validators.required, Validators.min(-90.0), Validators.max(90.0)],
          updateOn: 'change'
        }),
        longitudeControl: new FormControl<number>(0.0, {
          validators: [Validators.required, Validators.min(-180.0), Validators.max(180.0)],
          updateOn: 'change'
        })
      })
    });
    this.shouldPasswordBeHidden = signal<boolean>(true);
    this.shouldRepeatedPasswordBeHidden = signal<boolean>(true);
    this.userRegistrationRequest = new UserRegistrationRequest(null);
    this.isRegistrationFormSubmitted = signal<boolean>(false);
    this.hasEmailMessageForAccountActivationBeenSent = signal<boolean>(false);

    this.parametersOfSubmitUserRegistrationRequestFunction = new ParametersOfSubmitUserRegistrationRequestFunction(
        this.userRegistrationRequest, this.isRegistrationFormSubmitted, this.hasEmailMessageForAccountActivationBeenSent, 
        this.snackBar);
  }

  /** REFERENCE: https://material.angular.dev/components/form-field/examples */
  changeVisibilityOfPassword(event: MouseEvent): void {
    this.shouldPasswordBeHidden.set(!this.shouldPasswordBeHidden());

    event.stopPropagation();
  }

  /** REFERENCE: https://material.angular.dev/components/form-field/examples */
  changeVisibilityOfRepeatedPassword(event: MouseEvent): void {
    this.shouldRepeatedPasswordBeHidden.set(!this.shouldRepeatedPasswordBeHidden());

    event.stopPropagation();
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  submitUserRegistrationRequest(parameters: ParametersOfSubmitUserRegistrationRequestFunction): void {
    parameters.setIsRegistrationFormSubmitted(true);

    /* REFERENCES:<br />
     * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
     * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
    */
    parameters.getUserRegistrationRequest().setEmailAddress(this.registrationFormGroup.value['emailAddressControl']);
    parameters.getUserRegistrationRequest().setUsername(this.registrationFormGroup.value['usernameControl']);
    parameters.getUserRegistrationRequest().setPassword(this.registrationFormGroup.value['passwordControl']);
    parameters.getUserRegistrationRequest().setFirstName(this.registrationFormGroup.value['firstNameControl']);
    parameters.getUserRegistrationRequest().setLastName(this.registrationFormGroup.value['lastNameControl']);
    let enteredAddress: Address = new Address(null);
    enteredAddress.setStreet(this.registrationFormGroup.get('addressFormGroup')?.value['streetControl']);
    enteredAddress.setNumber(this.registrationFormGroup.get('addressFormGroup')?.value['numberControl']);
    enteredAddress.setPostalCode(this.registrationFormGroup.get('addressFormGroup')?.value['postalCodeControl']);
    enteredAddress.setPlace(this.registrationFormGroup.get('addressFormGroup')?.value['placeControl']);
    enteredAddress.setCountry(this.registrationFormGroup.get('addressFormGroup')?.value['countryControl']);
    enteredAddress.setLatitude(this.registrationFormGroup.get('addressFormGroup')?.value['latitudeControl']);
    enteredAddress.setLongitude(this.registrationFormGroup.get('addressFormGroup')?.value['longitudeControl']);
    parameters.getUserRegistrationRequest().setAddress(enteredAddress);

    this.registrationService.registerWith(parameters);
  }
}
