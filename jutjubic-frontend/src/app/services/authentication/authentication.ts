import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { JwtPayload, jwtDecode } from 'jwt-decode';

import { ObjectWithTextualContext } from '../../model/object-with-textual-context';
import { TokenWithLifeDuration } from '../../model/user/token-with-life-duration';
import { ParametersOfSubmitUserCredentialsFunction } from '../../utilities/parameters-of-submit-user-credentials-function';

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private urlOfLoggingOnMethod: string = 'http://localhost:8080/log-on';
  private token: string | null;

  constructor(private httpClient: HttpClient) {
    this.token = null;
  }

  isTokenPresent(): boolean {
    return this.token != undefined && this.token != null && this.token?.length > 0;
  }

  getToken(): string | null {
    return this.token;
  }

  setToken(token: string | null): void {
    this.token = token;
  }

  logOnWith(parameters: ParametersOfSubmitUserCredentialsFunction): void {
    const headersOfHttpRequestWithNonEmpthyBody: HttpHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    this.httpClient.post<ObjectWithTextualContext>(this.urlOfLoggingOnMethod, JSON.stringify(parameters.getUserCredentials()), {
        headers: headersOfHttpRequestWithNonEmpthyBody
    })
    // REFERENCE: https://rxjs.dev/deprecations/subscribe-arguments
    .subscribe({
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
        parameters.getSnackBar().open('Успешно сте пријављени на систем Јутјубића.', 'Затворите', { duration: 5000 });

        parameters.getRouter().navigateByUrl('/').then(() => { window.location.reload(); });
      },
      error(errorResponse: HttpErrorResponse): void {
        parameters.setIsLoggingOnFormSubmitted(false);

        let error: ObjectWithTextualContext = new ObjectWithTextualContext(errorResponse.error);
        console.log(`Error while logging in!\n\n${error.getTextualContext()}`);
        // REFERENCE: https://material.angular.dev/components/snack-bar/overview
        if (errorResponse.status == 406) {
          parameters.getSnackBar().open('Кориснички налог је онемогућен!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 423) {
          parameters.getSnackBar().open('Кориснички налог је закључан!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 400) {
          parameters.getSnackBar().open('Унето је неисправно корисничко име и/или лозинка!', 'Затворите', { duration: 5000 });

          return;
        }
        if (errorResponse.status == 422) {
          parameters.getSnackBar().open('Дошло је до унутрашње аутентификационе грешке! Молимо Вас, покушајте поново касније.', 
              'Затворите', { duration: 5000 });
        }
      }
    });
  }
}
