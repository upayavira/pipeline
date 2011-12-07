package org.odoko.pipeline.model;

public class Payload {

	private String contentType;
	private Object value;
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
