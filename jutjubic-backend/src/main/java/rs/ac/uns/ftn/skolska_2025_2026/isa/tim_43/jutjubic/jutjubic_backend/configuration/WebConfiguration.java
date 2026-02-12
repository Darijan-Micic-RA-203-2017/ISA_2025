package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Configuration()
@EnableWebMvc()
public class WebConfiguration implements WebMvcConfigurer {
	public WebConfiguration() {}

	@Override()
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200")
				/* REFERENCES:
				 * https://www.reddit.com/r/developersIndia/comments/1l9fj5w/help_facing_cors_issue_in_prod_even_after/
				 * https://stackoverflow.com/questions/36809528/spring-boot-cors-filter-cors-preflight-channel-did-not-succeed
				*/
				.allowedMethods("*")
				.maxAge(3600);
	}
}
