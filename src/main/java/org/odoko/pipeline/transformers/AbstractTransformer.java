package org.odoko.pipeline.transformers;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.model.Asset.AssetState;
import org.odoko.pipeline.pipelines.AbstractComponent;
import org.odoko.pipeline.pipelines.Consumer;
import org.odoko.pipeline.pipelines.PipelineException;

public abstract class AbstractTransformer extends AbstractComponent implements Transformer {

	private Class incomingClass;
	private Class outgoingClass;
	private Consumer nextComponent;

	protected void setOutgoingClass(Class clazz) {
		this.outgoingClass = clazz;
	}

	protected void setIncomingClass(Class clazz) {
		this.incomingClass = clazz;
	}
	
	public Class getOutgoingClass() {
		return this.outgoingClass;
	}
	
	public Class getIncomingClass() {
		return this.incomingClass;
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
		try {
			if (!validate(asset, this.outgoingClass)) {
				fail(asset, "asset output is not of type " + outgoingClass.getName());
				return;
			}
			if (this.nextComponent == null) {
				throw new PipelineException(asset, AssetState.FAILED, getLocation(), "Pipeline not yet wired");
			}
			this.nextComponent.consume(asset);
		} catch (PipelineException e) {
			e.addLocation(getLocation());
			throw e;
		}
	}

	@Override
	public void consumeBase(Asset asset) {
		if (validate(asset, this.incomingClass)) {
			consume(asset);
		} else {
			fail(asset, "asset is not of type " + incomingClass.getName());
		}
	}
	@Override
	public void consume(Asset asset) {
		next(asset);
	}
}
