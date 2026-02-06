package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.notification;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.notification.EmailService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TextualEmailMessageData;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe2/async_example<br />
 * https://mailtrap.io/blog/spring-send-email/
*/
@Service()
public class EmailServiceImplementation implements EmailService {
	private JavaMailSender javaMailSender;

	@Autowired()
	public EmailServiceImplementation(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override()
	public void sendTextualEmailMessageWith(TextualEmailMessageData textualEmailMessageData) 
			throws MailException {
		SimpleMailMessage textualEmailMessage = new SimpleMailMessage();

		textualEmailMessage.setBcc(textualEmailMessageData.getBcc());
		textualEmailMessage.setCc(textualEmailMessageData.getCc());
		textualEmailMessage.setFrom(textualEmailMessageData.getFrom());
		textualEmailMessage.setReplyTo(textualEmailMessageData.getReplyTo());
		/* REFERENCES:
		 * https://in.relation.to/2024/04/22/stop-using-date/
		 * https://medium.com/decisionbrain/dates-time-in-modern-java-4ed9d5848a3e
		 * https://medium.com/@ujjawalr/stop-using-java-util-date-heres-why-and-what-to-use-instead-a1e6023e3c58
		*/
		textualEmailMessage.setSentDate(
				new Date(textualEmailMessageData.getSentDate().toInstant().toEpochMilli()));
		textualEmailMessage.setSubject(textualEmailMessageData.getSubject());
		textualEmailMessage.setText(textualEmailMessageData.getText());
		textualEmailMessage.setTo(textualEmailMessageData.getTo());

		javaMailSender.send(textualEmailMessage);
	}
}
