package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.security.authentication;

import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TokenUtilities;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private TokenUtilities tokenUtilities;
	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtilities tokenUtilities, 
			UserDetailsService userDetailsService) {
		this.tokenUtilities = tokenUtilities;
		this.userDetailsService = userDetailsService;
	}

	@Override()
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		String authenticationToken = tokenUtilities.extractTokenFrom(request);

		try {
			if (authenticationToken != null) {
				String username = tokenUtilities.extractUsernameFrom(authenticationToken);

				if (username != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					if (tokenUtilities.isTokenValid(authenticationToken, userDetails)) {
						TokenBasedAuthentication tokenBasedAuthentication = 
								new TokenBasedAuthentication(userDetails);
						tokenBasedAuthentication.setToken(authenticationToken);
						SecurityContextHolder.getContext()
								.setAuthentication(tokenBasedAuthentication);
					}
				}
			}
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		}

		filterChain.doFilter(request, response);
	}
}
