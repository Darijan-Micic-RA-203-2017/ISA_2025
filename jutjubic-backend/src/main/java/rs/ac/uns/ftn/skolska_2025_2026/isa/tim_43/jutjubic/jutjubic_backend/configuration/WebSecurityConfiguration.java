package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.security.authentication.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.security.authentication.TokenAuthenticationFilter;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user.UserServiceImplementation;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TokenUtilities;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Configuration()
@EnableWebSecurity()
@EnableMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration {
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private TokenUtilities tokenUtilities;

	@Autowired()
	public WebSecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint, 
			TokenUtilities tokenUtilities) {
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.tokenUtilities = tokenUtilities;
	}

	@Bean()
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean()
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean()
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement((sessionMC) -> sessionMC
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		httpSecurity.exceptionHandling((exceptionHC) -> exceptionHC
				.authenticationEntryPoint(restAuthenticationEntryPoint));

		httpSecurity.authorizeHttpRequests((authorizationMRMR) -> authorizationMRMR
				.requestMatchers("/log-on", "/register/**").permitAll()
				.requestMatchers("/css/**", "/favicon.ico", "/images/**", "/js/**", "/static/**", 
						"/webjars/**").permitAll()
				.anyRequest().authenticated());

		httpSecurity.cors((corsC) -> corsC.configure(httpSecurity));

		httpSecurity.csrf((csrfC) -> csrfC.disable());

		// REFERENCE: https://stackoverflow.com/questions/78774925/spring-security-warning-how-to-fix-authenticationprovider-vs-userdetailsservic
		UserDetailsService userDetailsService = new UserServiceImplementation();
		httpSecurity.addFilterBefore(
				new TokenAuthenticationFilter(tokenUtilities, userDetailsService), 
				BasicAuthenticationFilter.class);

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		httpSecurity.authenticationProvider(authenticationProvider);

		return httpSecurity.build();
	}
}
