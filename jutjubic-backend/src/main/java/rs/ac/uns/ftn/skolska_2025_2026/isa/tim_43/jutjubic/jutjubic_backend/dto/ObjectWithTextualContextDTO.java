package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto;

public class ObjectWithTextualContextDTO {
	private Object object;
	private String textualContext;

	public ObjectWithTextualContextDTO() {}

	public ObjectWithTextualContextDTO(Object object, String textualContext) {
		this.object = object;
		this.textualContext = textualContext;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getTextualContext() {
		return textualContext;
	}

	public void setTextualContext(String textualContext) {
		this.textualContext = textualContext;
	}
}
