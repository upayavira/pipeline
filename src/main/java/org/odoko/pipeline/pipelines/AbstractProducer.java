package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Producer;

public abstract class AbstractProducer implements Producer {

	private Map<String, String>properties = new HashMap<String, String>();
	
	@Override
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}

	private String outgoingContentType;
	private Consumer nextComponent;
	
	@Override
	public void wire(Consumer consumer) {
		this.nextComponent = consumer;
	}

	protected void setOutgoingContentType(String contentType) {
		this.outgoingContentType = contentType;
	}

	public String getOutgoingContentType() {
		return this.outgoingContentType;
	}
	
	@Override
	public void process(Asset asset) {
		next(asset);
	}
	
	@Override
	public void next(Asset asset) {
		this.nextComponent.consume(asset);
	}

}
