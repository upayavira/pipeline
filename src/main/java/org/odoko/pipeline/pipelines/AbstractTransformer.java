package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractTransformer implements Consumer, Producer {

	private String incomingContentType;
	private String outgoingContentType;
	private Consumer nextComponent;

	private Map<String, String>properties = new HashMap<String, String>();
	
	@Override
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}

	protected void setOutgoingContentType(String contentType) {
		this.outgoingContentType = contentType;
	}

	protected void setIncomingContentType(String contentType) {
		this.incomingContentType = contentType;
	}
	
	public String getOutgoingContentType() {
		return this.outgoingContentType;
	}
	
	public String getIncomingContentType() {
		return this.incomingContentType;
	}
	
	@Override
	public void wire(Consumer consumer) {
		this.nextComponent = consumer;
	}

	@Override
	public void process(Asset asset) {
        next(asset);
	}

	@Override
	public void next(Asset asset) {
		this.nextComponent.consume(asset);
	}
	@Override
	public void consume(Asset asset) {
		// TODO Auto-generated method stub
		
	}

}
