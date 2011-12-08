package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractConsumer implements Consumer {

	private String incomingContentType;

	private Map<String, String>properties = new HashMap<String, String>();
	
	@Override
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}

	protected void setIncomingContentType(String contentType) {
		this.incomingContentType = contentType;
	}

	public String getIncomingContentType() {
		return this.incomingContentType;
	}
	
	@Override
	public abstract void consume(Asset asset);
	
}
