import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ObjectWithTextualContext } from '../../model/object-with-textual-context';
import { User } from '../../model/user/user';
import { ParametersOfActivateAccountOfUserFunction } from '../../utilities/parameters-of-activate-account-of-user-function';
import { ParametersOfSubmitUserRegistrationRequestFunction } from '../../utilities/parameters-of-submit-user-registration-request-function';

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private urlOfRegistrationMethod: string = 'http://localhost:8080/register';
  private urlOfAccountActivationMethod: string = this.urlOfRegistrationMethod.concat('/activate-account');

  constructor(private httpClient: HttpClient) { }

  registerUserBasedOn(parameters: ParametersOfSubmitUserRegistrationRequestFunction): void {
    const headersOfHttpRequestWithNonEmpthyBody: HttpHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    this.httpClient.post<ObjectWithTextualContext>(this.urlOfRegistrationMethod, 
        JSON.stringify(parameters.getUserRegistrationRequest()), {
            headers: headersOfHttpRequestWithNonEmpthyBody
    })
    // REFERENCE: https://rxjs.dev/deprecations/subscribe-arguments
    .subscribe({
      next(responseObject: ObjectWithTextualContext): void {
        parameters.setHasEmailMessageForAccountActivationBeenSent(true);

        /* REFERENCES:<br />
         * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
         * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
        */
        let objectWithTextualContext: ObjectWithTextualContext = new ObjectWithTextualContext(responseObject);
        let newUser: User = new User(objectWithTextualContext.getObject());
        console.log('Registration response:', newUser);
      },
      error(errorResponse: HttpErrorResponse): void {
        parameters.setIsRegistrationFormSubmitted(false);
        parameters.setHasEmailMessageForAccountActivationBeenSent(false);

        let error: ObjectWithTextualContext = new ObjectWithTextualContext(errorResponse.error);
        let textualContext: string = error.getTextualContext();
        console.log(`Error on registration!\n\n${textualContext}`);
        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        if (errorResponse.status == 406) {
          if (textualContext.includes('e-mail address')) {
            parameters.getSnackBar().open('Унета адреса електронске поште је већ повезана с неким корисником!', 'Затворите');
          } else {
            parameters.getSnackBar().open('Унето корисничко име је већ повезано с неким корисником!', 'Затворите');
          }

          return;
        }
        if (errorResponse.status == 409) {
          parameters.getSnackBar().open(
              'Дошло је до грешке при слању електронске поруке с повезницом за омогућавање деловања Вашег корисничког налога!' 
                  + ' Због те грешке, регистрација је поништена! Молимо Вас, покушајте поново касније.', 
              'Затворите');

          return;
        }
        if (errorResponse.status == 500) {
          parameters.getSnackBar().open('Дошло је до унутрашње грешке на услуживачу! Молимо Вас, покушајте поново касније.', 
              'Затворите');
        }
      }
    });
  }

  activateAccountOfUserWith(parameters: ParametersOfActivateAccountOfUserFunction): void {
    const headersOfHttpRequestWithNonEmpthyBody: HttpHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    this.httpClient.put<ObjectWithTextualContext>(this.urlOfAccountActivationMethod, 
        JSON.stringify(parameters.getWrappedDigestedIdentificator()), {
            headers: headersOfHttpRequestWithNonEmpthyBody
    })
    // REFERENCE: https://rxjs.dev/deprecations/subscribe-arguments
    .subscribe({
      next(responseObject: ObjectWithTextualContext): void {
        parameters.setHasAccountBeenActivated(true);

        /* REFERENCES:<br />
         * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
         * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
        */
        let objectWithTextualContext: ObjectWithTextualContext = new ObjectWithTextualContext(responseObject);
        let activatedUser: User = new User(objectWithTextualContext.getObject());
        console.log('Activation of account response:', activatedUser);
      },
      error(errorResponse: HttpErrorResponse): void {
        parameters.setHasAccountBeenActivated(false);

        let error: ObjectWithTextualContext = new ObjectWithTextualContext(errorResponse.error);
        let textualContext: string = error.getTextualContext();
        console.log(`Error on activation of account!\n\n${textualContext}`);
        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        if (textualContext.includes('no user')) {
          parameters.getSnackBar().open('Не постоји корисник с таквим провареним препознавачем!', 'Затворите');
        } else {
          parameters.getSnackBar().open('Корисничком налогу је већ омогућено деловање!', 'Затворите');
        }
      }
    });
  }
}
