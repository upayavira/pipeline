package org.odoko.pipeline.pipelines;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.odoko.pipeline.collectors.MockCollector;
import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.YamlConfiguration;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.locators.MockLocator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.transformers.MockBreakingTransformer;
import org.odoko.pipeline.transformers.MockTransformer;

public class BrokenPipelineTest {

	@Test
	public void testBrokenPipelineCorrectlyFails() throws IOException, ConfigurationException {
		Pipeline pipeline = new Pipeline();
		pipeline.addComponent(new MockCollector());
		pipeline.addComponent(new MockTransformer());
		pipeline.addComponent(new MockBreakingTransformer());
		pipeline.addComponent(new MockDispatcher());
		pipeline.wire();
		
		Locator locator = new MockLocator();
		
		List<Asset> assets = locator.locate();
		for (Asset asset : assets) {
			try {
				pipeline.process(asset);
			} catch (PipelineException e) {
				assertEquals(3, e.getPipelineStackTrace().size());
			}
		}
		
		List<Component> components = pipeline.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertEquals("Incorrect number of assets dispatched", 0, finalAssets.size());
	}
}
