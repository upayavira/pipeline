package org.odoko.pipeline.pipelines;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.YamlConfiguration;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.model.Asset;

public class ConfiguredPipelineTest {

	@Test
	public void testMockPipelineBuiltFromYamlConfiguration() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample1.yaml");
		PipelineBuilder builder = new PipelineBuilder();
		Pipeline pipeline = builder.build(config, config.getPipeline("default"));
		
		Locator locator = builder.buildLocator(config, config.getLocator("default"));
		
		List<Asset> assets = locator.locate();
		for (Asset asset : assets) {
			pipeline.process(asset);
		}
		
		List<Component> components = pipeline.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertEquals("Incorrect number of assets dispatched", 10, finalAssets.size());
		assertEquals("First asset not text/plain", "text/plain", finalAssets.get(0).getContentType());
		assertEquals("First asset is not upper-case, transformer did not work", "CONTENT FROM URL HTTP://WWW.ODOKO.ORG/ARTICLE-0", finalAssets.get(0).getValue());
	}
	
}
