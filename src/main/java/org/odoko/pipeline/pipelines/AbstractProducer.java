package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Producer;

public abstract class AbstractProducer extends AbstractComponent implements Producer {

	private Class outgoingClass;
	private Consumer nextComponent;
	
	@Override
	public void wire(Consumer consumer) {
		this.nextComponent = consumer;
	}

	protected void setOutgoingClass(Class clazz) {
		this.outgoingClass = clazz;
	}

	public Class getOutgoingClass() {
		return this.outgoingClass;
	}
	
	@Override
	public void process(Asset asset) {
		next(asset);
	}
	
	@Override
	public void next(Asset asset) {
		try {
			if (validate(asset, this.outgoingClass)) {
				this.nextComponent.consume(asset);
			} else {
				fail(asset, "asset output is not of type " + outgoingClass.getName());
			}
		} catch (PipelineException e) {
			e.addLocation(this.getLocation());
			throw e;
		}
	}
}
