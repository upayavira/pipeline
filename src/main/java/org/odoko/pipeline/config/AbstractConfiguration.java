package org.odoko.pipeline.config;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.pipelines.Pipeline;

public abstract class AbstractConfiguration implements Configuration {

	private Map<String, String> properties = new HashMap<String, String>();
	private Map<String, ConfiguredComponent> components = new HashMap<String, ConfiguredComponent>();
	private Map<String, ConfiguredComponent> locators = new HashMap<String, ConfiguredComponent>();
	private Map<String, Pipeline> pipelines = new HashMap<String, Pipeline>();
	
	@Override
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public String getProperty(String name) {
		return properties.get(name);
	}

	@Override
	public ConfiguredComponent getComponent(String key) {
		return components.get(key);
	}

	@Override
	public ConfiguredComponent getLocator(String key) {
		return locators.get(key);
	}

	@Override
	public Pipeline getPipeline(String name) {
		return pipelines.get(name);
	}

	@Override
	public void addLocator(String name, ConfiguredComponent locator) {
		this.locators.put(name, locator);
	}

	@Override
	public void addPipeline(String name, Pipeline pipeline) {
		this.pipelines.put(name, pipeline);
	}

	@Override
	public void addComponent(String name, ConfiguredComponent component) {
		this.components.put(name, component);
	}

}
