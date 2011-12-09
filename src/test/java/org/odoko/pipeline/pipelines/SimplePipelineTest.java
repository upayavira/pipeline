package org.odoko.pipeline.pipelines;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.odoko.pipeline.collectors.Collector;
import org.odoko.pipeline.collectors.MockCollector;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.locators.MockLocator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.transformers.MockTransformer;

public class SimplePipelineTest {

	@Test
	public void testLocatorCollectorDispatcherPipeline() {
		Pipeline pipeline = new Pipeline();
		Locator locator = new MockLocator();
		Collector collector = new MockCollector();
		MockDispatcher dispatcher = new MockDispatcher();
		pipeline.addComponent(collector);
		pipeline.addComponent(dispatcher);
		
		pipeline.wire();
		List<Asset> assets = locator.locate();
		for (Asset asset : assets) {
			pipeline.process(asset);
		}
		
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertTrue("Incorrect number of assets dispatched", finalAssets.size()==10);
		assertTrue("First asset not text/plain", finalAssets.get(0).getContentType().equals("text/plain"));
	}

	@Test
	public void testLocatorCollectorTransformerDispatcherPipeline() {
		Pipeline pipeline = new Pipeline();
		Locator locator = new MockLocator();
		Collector collector = new MockCollector();
		Transformer transformer = new MockTransformer();
		MockDispatcher dispatcher = new MockDispatcher();
		pipeline.addComponent(collector);
		pipeline.addComponent(transformer);
		pipeline.addComponent(dispatcher);
		
		pipeline.wire();
		List<Asset> assets = locator.locate();
		for (Asset asset : assets) {
			pipeline.process(asset);
		}
		
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertTrue("Incorrect number of assets dispatched", finalAssets.size()==10);
		assertTrue("First asset not text/plain", finalAssets.get(0).getContentType().equals("text/plain"));
		assertTrue("First asset is not upper-case, transformer did not work", finalAssets.get(0).getValue().equals("CONTENT FROM URL HTTP://WWW.ODOKO.ORG/ARTICLE-0"));
	}
}
