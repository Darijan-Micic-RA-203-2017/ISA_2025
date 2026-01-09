package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCES:<br/>
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br/>
 * https://www.javacodegeeks.com/rest-api-security-with-spring-security-jwt-token-signing.html
*/
@Component()
public class TokenUtilities {
	private static final String AUDIENCE = "web";
	private static final MacAlgorithm SIGNATURE_ALGORITHM = SIG.HS512;

	@Value(value = "${spring.application.name}")
	private String nameOfApplication;

	@Value(value = "${secret-of-application}")
	private String secretOfApplication;

	@Value(value = "${life-duration-of-token-in-milliseconds}")
	private int lifeDurationOfTokenInMilliseconds;

	@Value(value = "${name-of-auth-header}")
	private String authHeader;

	public TokenUtilities() {}

	public int getLifeDurationOfTokenInMilliseconds() {
		return lifeDurationOfTokenInMilliseconds;
	}

	private String generateAudience() {
		return AUDIENCE;
	}

	/** REFERENCES:<br />
	 * https://in.relation.to/2024/04/22/stop-using-date/
	 * https://medium.com/decisionbrain/dates-time-in-modern-java-4ed9d5848a3e<br />
	 * https://medium.com/@ujjawalr/stop-using-java-util-date-heres-why-and-what-to-use-instead-a1e6023e3c58<br />
	 * https://mkyong.com/java/how-to-get-current-timestamps-in-java/
	*/
	private ZonedDateTime generateDateAndTimeOfTokenExpiration() {
		return ZonedDateTime.now().plusNanos(1000000 * getLifeDurationOfTokenInMilliseconds());
	}

	private SecretKey generateSigningKey() {
		byte[] keyBytes = secretOfApplication.getBytes(StandardCharsets.UTF_8);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateTokenForUserWith(String username) {
		return Jwts.builder()
				.issuer(nameOfApplication)
				.subject(username)
				.audience().add(generateAudience()).and()
				.issuedAt(new Date())
				// REFERENCE: https://mkyong.com/java/how-to-get-current-timestamps-in-java/
				.expiration(new Date(generateDateAndTimeOfTokenExpiration().getNano() / 1000000))
				.signWith(generateSigningKey(), SIGNATURE_ALGORITHM)
				.compact();
	}

	public String extractValueOfAuthHeaderFromHeaderOf(HttpServletRequest request) {
		return request.getHeader(authHeader);
	}

	public String extractTokenFrom(HttpServletRequest request) {
		String authHeader = extractValueOfAuthHeaderFromHeaderOf(request);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	private Claims extractAllClaimsFrom(String token) {
		Claims claims = null;

		try {
			claims = Jwts.parser()
					.verifyWith(generateSigningKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return claims;
	}

	public String extractUsernameFrom(String token) {
		String username = null;

		try {
			final Claims claims = extractAllClaimsFrom(token);
			username = claims.getSubject();
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return username;
	}

	public Date extractDateAndTimeOfTokenIssuingFrom(String token) {
		Date dateAndTimeOfTokenIssuing = null;

		try {
			final Claims claims = extractAllClaimsFrom(token);
			dateAndTimeOfTokenIssuing = claims.getIssuedAt();
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dateAndTimeOfTokenIssuing;
	}

	public String extractAudienceFrom(String token) {
		String audience = null;

		try {
			final Claims claims = extractAllClaimsFrom(token);
			audience = claims.getAudience().iterator().next();
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return audience;
	}

	public Date extractDateAndTimeOfTokenExpirationFrom(String token) {
		Date dateAndTimeOfTokenExpiration = null;

		try {
			final Claims claims = extractAllClaimsFrom(token);
			dateAndTimeOfTokenExpiration = claims.getExpiration();
		} catch (ExpiredJwtException eJWTE) {
			System.out.println(eJWTE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dateAndTimeOfTokenExpiration;
	}

	private boolean isTokenCreatedBeforeDateAndTimeOfLastPasswordChange(
			ZonedDateTime dateAndTimeOfTokenIssuing, 
			ZonedDateTime dateAndTimeOfLastPasswordChange) {
		return (dateAndTimeOfLastPasswordChange != null 
				&& dateAndTimeOfTokenIssuing.isBefore(dateAndTimeOfLastPasswordChange));
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = extractUsernameFrom(token);
		final Date dateAndTimeOfTokenIssuing = extractDateAndTimeOfTokenIssuingFrom(token);

		return (username != null && username.equals(user.getUsername()) 
				&& !isTokenCreatedBeforeDateAndTimeOfLastPasswordChange(
						// REFERENCE: https://mkyong.com/java/how-to-get-current-timestamps-in-java/
						ZonedDateTime.ofInstant(dateAndTimeOfTokenIssuing.toInstant(), 
								ZoneId.systemDefault()), 
						user.getDateAndTimeOfLastPasswordChange()));
	}
}
