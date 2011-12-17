package org.odoko.pipeline.config;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.odoko.pipeline.dispatchers.MockDispatcher;

import static org.junit.Assert.assertEquals;

public class YamlConfigurationTest {
	
	@Test
	public void testYamlConfiguration() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample1.yaml");
		
		ConfiguredComponent component = config.getComponent("mocktransformer");
		ConfiguredComponent locator = config.getLocator("default");
		ConfiguredPipeline pipeline = config.getPipeline("default");
		List<ConfiguredComponent> pipelineComponents = pipeline.getComponents();
		
		assertEquals("org.odoko.pipeline.transformers.MockTransformer", component.getClassName());
		assertEquals("locator", locator.getType());
		assertEquals("http://www.somesite.com/feed.rss", locator.getProperty("url"));
		assertEquals(3, pipelineComponents.size());
		assertEquals("someapp.com", pipelineComponents.get(2).getProperty("dispatch-host"));
	}

}
