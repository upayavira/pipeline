package org.odoko.pipeline.pipelines;

import java.util.List;
import java.util.Map;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.ConfiguredComponent;
import org.odoko.pipeline.config.ConfiguredPipeline;
import org.odoko.pipeline.locators.Locator;

public class PipelineBuilder {

	public Pipeline build(Configuration configuration, ConfiguredPipeline configuredPipeline) throws ConfigurationException {
		Pipeline pipeline = new Pipeline();
		List<ConfiguredComponent> configuredComponents = configuredPipeline.getComponents();
		for (ConfiguredComponent configuredComponent : configuredComponents) {
			Component component = buildComponent(configuration, configuredComponent);
			pipeline.addComponent(component);
		}
		pipeline.wire();
		return pipeline;
	}
	
	public Locator buildLocator(Configuration configuration, ConfiguredComponent configuredLocator) throws ConfigurationException {
		try {
			String className = configuredLocator.getClassName();
			if (className == null) {
				ConfiguredComponent rootLocator = configuration.getComponent(configuredLocator.getProperty("component"));
				className = rootLocator.getClassName();
			}
			Class locatorClass = Class.forName(className);
			Locator locator = (Locator)locatorClass.newInstance();
			Map<String, String> properties = configuredLocator.getProperties();
			for (String name : properties.keySet()) {
				locator.setProperty(name, properties.get(name));
			}
			return locator;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredLocator.getClassName());
		} catch (IllegalAccessException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredLocator.getClassName());
		} catch (InstantiationException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredLocator.getClassName());
		}
	}
	
	private Component buildComponent(Configuration configuration, ConfiguredComponent configuredComponent) throws ConfigurationException {
		try {
			String className = configuredComponent.getClassName();
			if (className == null) {
				ConfiguredComponent rootComponent = configuration.getComponent(configuredComponent.getName());
				className = rootComponent.getClassName();
			}
			Class componentClass = Class.forName(className);
			Component component = (Component)componentClass.newInstance();
			Map<String, String> properties = configuredComponent.getProperties();
			for (String name : properties.keySet()) {
				component.setProperty(name, properties.get(name));
			}
			return component;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		} catch (IllegalAccessException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		} catch (InstantiationException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		}
	}
}
