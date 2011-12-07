package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractTransformer implements Consumer, Producer {

	private String incomingContentType;
	private String outgoingContentType;
	private Consumer nextComponent;
	
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
