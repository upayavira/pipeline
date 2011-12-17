package org.odoko.pipeline.branchers;

import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.model.Asset;

public class MockBrancher extends AbstractBrancher {

	@Override
	public void consume(Asset asset) {
		AssetHandler handler = getPipeline().getHandler();
		handler.addAsset(getQueue(), asset);
	}

}
