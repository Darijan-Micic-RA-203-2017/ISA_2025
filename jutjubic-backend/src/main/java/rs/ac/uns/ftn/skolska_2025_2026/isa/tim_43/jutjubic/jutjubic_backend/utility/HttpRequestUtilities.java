package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** REFERENCES:<br />
 * https://stackoverflow.com/questions/4678797/how-do-i-get-the-remote-address-of-a-client-in-servlet
*/
public class HttpRequestUtilities {
	private static final List<String> requestHeadersThatCouldContainIpAddressOfUser 
			= Arrays.asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", 
					"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

	public HttpRequestUtilities() {}

	/** REFERENCE: https://stackoverflow.com/questions/4678797/how-do-i-get-the-remote-address-of-a-client-in-servlet */
	public static String extractIpAddressOfUserFrom(HttpServletRequest request) {
		return requestHeadersThatCouldContainIpAddressOfUser.stream()
				.map(request::getHeader)
				.filter(Objects::nonNull)
				.filter(ipAddress -> !ipAddress.isBlank() && !ipAddress.equalsIgnoreCase("unknown"))
				.findFirst().orElseGet(request::getRemoteAddr);
	}
}
