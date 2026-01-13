import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ObjectWithTextualContext } from '../../model/object-with-textual-context';
import { UserCredentials } from '../../model/user/user-credentials';

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

  logOnWith(userCredentials: UserCredentials): Observable<ObjectWithTextualContext> {
    const headersOfHttpRequestWithNonEmpthyBody: HttpHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    return this.httpClient.post<ObjectWithTextualContext>(this.urlOfLoggingOnMethod, JSON.stringify(userCredentials), {
      headers: headersOfHttpRequestWithNonEmpthyBody
    });
  }
}
