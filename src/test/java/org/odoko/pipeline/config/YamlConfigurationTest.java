package org.odoko.pipeline.config;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.odoko.pipeline.pipelines.Pipeline;

public class YamlConfigurationTest {
	
	@Test
	public void testYamlConfiguration() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample1.yaml");
		
		ConfiguredComponent component = config.getComponent("mocktransformer");
		ConfiguredComponent locator = config.getLocator("default");
		Pipeline pipeline = config.getPipeline("default");
		
		assertEquals(component.getClassName(), "org.odoko.pipeline.transformers.MockTransformer");
		assertEquals(locator.getType(), "locator");
		assertEquals(locator.getProperty("url"), "http://www.somesite.com/feed.rss");
		assertEquals(pipeline.getTransformers().size(), 1);
	}

}
