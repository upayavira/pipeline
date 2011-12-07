package org.odoko.pipeline.locators;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractProducer;

public class MockLocator extends AbstractProducer implements Locator {

	private static final String URI_PREFIX = "http://www.odoko.org/article-"; 
	@Override
	public List<Asset> locate() {
		List<Asset>assets = new ArrayList<Asset>();
		for (int i=0; i<10; i++) {
			Asset asset = new Asset();
			asset.setUri(URI_PREFIX + i);
			asset.setContentType("text/plain");
			assets.add(asset);
		}
		return assets;
	}
}
