package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractConsumer extends AbstractComponent implements Consumer {

	private Class incomingClass;

	protected void setIncomingClass(Class clazz)  {
		this.incomingClass = clazz;
	}

	public Class getIncomingClass() {
		return this.incomingClass;
	}
	
	@Override
	public void consumeBase(Asset asset) {
		if (validate(asset, this.incomingClass)) {
			consume(asset);
		} else {
			fail(asset, "asset is not of type " + incomingClass.getName());
		}
	}
}
