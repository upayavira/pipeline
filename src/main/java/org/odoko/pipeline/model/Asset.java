package org.odoko.pipeline.model;

import java.util.HashMap;
import java.util.Map;

public class Asset {

	public static enum AssetState {
		NEW,
		ACTIVE,
		SUCCESS,
		FAILED,
		BRANCHED,
		CANCELLED,
	}

	private String contentType;
	private String uri;
	private Object value;
	private Map<String, Payload> payloads = new HashMap<String, Payload>();
	private Map<String, String> properties = new HashMap<String, String>();
	private AssetState state = AssetState.NEW;
	
	public void setState(AssetState state) {
		this.state = state;
	}
	
	public AssetState getState() {
		return this.state;
	}
	
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
	
	public void setProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}
}
