package org.odoko.pipeline.model;

import java.util.Map;

public class Asset {

	private String contentType;
	private String uri;
	private Object value;
	private Map<String, Payload> payloads;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

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
	
	public Payload getPayload(String name) {
		return payloads.get(name);
	}
	
	public void removePayload(String name) {
		payloads.remove(name);
	}
	
	public void addPayload(String name, Payload payload) {
		payloads.put(name, payload);
	}
	
	
}
