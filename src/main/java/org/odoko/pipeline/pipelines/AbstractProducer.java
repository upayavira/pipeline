package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Producer;

public abstract class AbstractProducer extends AbstractComponent implements Producer {

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
