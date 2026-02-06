package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility;

import java.time.ZonedDateTime;

public class TextualEmailMessageData {
	private String[] bcc;
	private String[] cc;
	private String from;
	private String replyTo;
	/** REFERENCES:<br />
	 * https://in.relation.to/2024/04/22/stop-using-date/<br />
	 * https://medium.com/decisionbrain/dates-time-in-modern-java-4ed9d5848a3e<br />
	 * https://medium.com/@ujjawalr/stop-using-java-util-date-heres-why-and-what-to-use-instead-a1e6023e3c58
	*/
	private ZonedDateTime sentDate;
	private String subject;
	private String text;
	private String[] to;

	public TextualEmailMessageData() {}

	public TextualEmailMessageData(String[] bcc, String[] cc, String from, String replyTo, 
			ZonedDateTime sentDate, String subject, String text, String[] to) {
		this.bcc = bcc;
		this.cc = cc;
		this.from = from;
		this.replyTo = replyTo;
		this.sentDate = sentDate;
		this.subject = subject;
		this.text = text;
		this.to = to;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public ZonedDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(ZonedDateTime sentDate) {
		this.sentDate = sentDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}
}
