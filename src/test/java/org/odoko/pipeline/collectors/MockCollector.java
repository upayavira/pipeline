package org.odoko.pipeline.collectors;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractTransformer;

public class MockCollector extends AbstractTransformer implements Collector {

	public MockCollector() {
		setIncomingContentType("text/plain");
		setOutgoingContentType("text/plain");

	}

	@Override
	public void process(Asset asset) {
		asset.setValue("Content from URL " + asset.getUri());
		next(asset);
	}
}
