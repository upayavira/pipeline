package org.odoko.pipeline.pipelines;

public abstract class AbstractConsumer extends AbstractComponent implements Consumer {

	private String incomingContentType;

	protected void setIncomingContentType(String contentType) {
		this.incomingContentType = contentType;
	}

	public String getIncomingContentType() {
		return this.incomingContentType;
	}
	
}
