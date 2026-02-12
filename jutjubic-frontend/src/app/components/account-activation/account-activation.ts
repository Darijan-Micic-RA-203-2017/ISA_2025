import { Component, inject, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, Params, RouterLink } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';

import { RegistrationService } from '../../services/registration/registration';

import { WrappedEncodedId } from '../../model/user/wrapped-encoded-id';
import { ParametersOfActivateAccountOfUserFunction } from '../../utilities/parameters-of-activate-account-of-user-function';

@Component({
  standalone: true,
  // REFERENCE: https://stackoverflow.com/questions/78168666/how-can-i-solve-this-error-usging-routerlink-in-angular-17-2/78168794
  imports: [RouterLink, MatButtonModule, MatCardModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './account-activation.html',
  styleUrl: './account-activation.css',
})
export class AccountActivationComponent {
  // REFERENCE: https://angular.dev/guide/routing/read-route-state
  wrappedEncodedId: WrappedEncodedId = new WrappedEncodedId({ encodedId: '' });
  activatedRoute: ActivatedRoute = inject<ActivatedRoute>(ActivatedRoute);

  /* REFERENCES:
   * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
   * https://angular.dev/essentials/signals
   * https://angular.dev/guide/signals
  */
  hasAccountBeenActivated: WritableSignal<boolean>;

  // REFERENCE: https://material.angular.dev/components/snack-bar/overview
  snackBar: MatSnackBar = inject<MatSnackBar>(MatSnackBar);

  parametersOfActivateAccountOfUserFunction: ParametersOfActivateAccountOfUserFunction;

  constructor(private registrationService: RegistrationService) {
    // REFERENCE: https://angular.dev/guide/routing/read-route-state
    this.activatedRoute.params.subscribe((params: Params) => {
      this.wrappedEncodedId.setEncodedId(params['encoded_id']);
    });
    this.hasAccountBeenActivated = signal<boolean>(false);

    this.parametersOfActivateAccountOfUserFunction = new ParametersOfActivateAccountOfUserFunction(this.wrappedEncodedId, 
        this.hasAccountBeenActivated, this.snackBar);

    this.activateAccountOfUser();
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  activateAccountOfUser(): void {
    this.registrationService.activateAccountOfUserWith(this.parametersOfActivateAccountOfUserFunction);
  }
}
