package org.odoko.pipeline.config;

import java.io.IOException;

import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.pipelines.Component;
import org.odoko.pipeline.pipelines.Pipeline;

public interface Configuration {

	public void parse(String filename) throws IOException, ConfigurationException;
	public Pipeline getPipeline(String name);
	public void addPipeline(String name, Pipeline pipeline);
	public ConfiguredComponent getComponent(String string);
	public ConfiguredComponent getLocator(String string);
	public void addComponent(String name, ConfiguredComponent component);
	public void setProperty(String name, String value);
	public String getProperty(String name);
	public void addLocator(String name, ConfiguredComponent locator);
}
