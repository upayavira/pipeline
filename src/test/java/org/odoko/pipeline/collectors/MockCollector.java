package org.odoko.pipeline.collectors;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.transformers.AbstractTransformer;

public class MockCollector extends AbstractTransformer implements Collector {

	public MockCollector() {
		setIncomingClass(void.class);
		setOutgoingClass(String.class);
	}

	@Override
	public void process(Asset asset) {
		asset.setValue("Content from URL " + asset.getUri());
		next(asset);
	}
}
