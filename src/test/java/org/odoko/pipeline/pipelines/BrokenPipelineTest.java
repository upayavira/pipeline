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

public class BrokenPipelineTest {

	@Test
	public void testMockPipelineBuiltFromYamlConfiguration() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample4.yaml");
		PipelineBuilder builder = new PipelineBuilder();
		Pipeline pipeline = builder.build(config, config.getPipeline("default"));
		
		Locator locator = builder.buildLocator(config, config.getLocator("default"));
		
		try {
			List<Asset> assets = locator.locate();
			for (Asset asset : assets) {
				pipeline.process(asset);
			}
		} catch (PipelineException e) {
			assertEquals(3, e.getPipelineStackTrace().size());
		}
		
		List<Component> components = pipeline.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertEquals("Incorrect number of assets dispatched", 0, finalAssets.size());
	}
	
	@Test
	public void testMockPipelineBuiltFromYamlConfigurationConfiguredWithProperties() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample1.yaml");
		PipelineBuilder builder = new PipelineBuilder();
		Pipeline pipeline = builder.build(config, config.getPipeline("default"));
		
		List<Component> components = pipeline.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
	    assertEquals("someapp.com", dispatcher.getProperty("dispatchhost")); 

	    Locator locator = builder.buildLocator(config, config.getLocator("default"));
		assertEquals("http://www.somesite.com/feed.rss", locator.getProperty("url"));
		assertEquals("superduper", locator.getProperty("locatortype"));
	}
	
	@Test
	public void testMockPipelineBuiltFromYamlConfigConfiguredWithPropertiesAndVariableSubstitution() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample2.yaml");
		PipelineBuilder builder = new PipelineBuilder();
		Pipeline pipeline = builder.build(config, config.getPipeline("default"));
		
		List<Component> components = pipeline.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
	    assertEquals("app.odoko.org", dispatcher.getProperty("dispatch.host")); 

	}
}
