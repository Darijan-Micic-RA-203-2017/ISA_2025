package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.security.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Component()
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	public RestAuthenticationEntryPoint() {}

	@Override()
	public void commence(HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}
}
