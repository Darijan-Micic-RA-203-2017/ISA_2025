import { HttpHeaders } from "@angular/common/http";

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class HeadersConstants {
	public static headersOfHttpRequestWithEmpthyBody: HttpHeaders = new HttpHeaders({
		'Accept': 'application/json'
	});
	public static headersOfHttpRequestWithNonEmpthyBody: HttpHeaders = new HttpHeaders({
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	});

	constructor() { }
}
