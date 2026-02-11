package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class WrappedEncodedIdDTO {
	@NotBlank(message = "The encoded ID has to be non-blank!")
	@Pattern(regexp = "^\\S+$", 
			message = "The encoded ID has to not contain any white-space characters!")
	@Size(min = 16, max = 60, 
			message = "The encoded ID has to contain at least 16 and at most 60 characters!")
	private String encodedId;

	public WrappedEncodedIdDTO() {}

	public WrappedEncodedIdDTO(String encodedId) {
		this.encodedId = encodedId;
	}

	public String getEncodedId() {
		return encodedId;
	}

	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}
}
