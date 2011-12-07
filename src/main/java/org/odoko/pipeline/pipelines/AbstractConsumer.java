package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractConsumer implements Consumer {

	private String incomingContentType;
	
	protected void setIncomingContentType(String contentType) {
		this.incomingContentType = contentType;
	}

	public String getIncomingContentType() {
		return this.incomingContentType;
	}
	
	@Override
	public abstract void consume(Asset asset);
	
}
