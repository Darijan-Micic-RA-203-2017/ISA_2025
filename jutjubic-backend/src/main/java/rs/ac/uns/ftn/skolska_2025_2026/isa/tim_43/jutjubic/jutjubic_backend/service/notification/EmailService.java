package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.notification;

import org.springframework.mail.MailException;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TextualEmailMessageData;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe2/async_example<br />
 * https://mailtrap.io/blog/spring-send-email/
*/
public interface EmailService {
	void sendTextualEmailMessageWith(TextualEmailMessageData textualEmailMessageData) 
			throws MailException;
}
