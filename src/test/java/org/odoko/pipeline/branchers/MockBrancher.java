package org.odoko.pipeline.branchers;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractConsumer;

public class MockBrancher extends AbstractConsumer{

	private String queue;
	
	@Override
	public void initialise(Configuration config) {
	    queue = getProperty("queue");
	}

	@Override
	public void consume(Asset asset) {
		AssetHandler handler = getPipeline().getHandler();
		handler.addAsset(queue, asset);
	}

}
