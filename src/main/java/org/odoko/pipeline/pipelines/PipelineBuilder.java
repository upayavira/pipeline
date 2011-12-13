package org.odoko.pipeline.pipelines;

import java.util.List;
import java.util.Map;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.ConfiguredComponent;
import org.odoko.pipeline.config.ConfiguredPipeline;
import org.odoko.pipeline.config.VariableResolver;
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
			ConfiguredComponent rootLocator = null;
			String className = configuredLocator.getClassName();
			if (className == null) {
				rootLocator = configuration.getComponent(configuredLocator.getProperty("component"));
				className = rootLocator.getClassName();
			}
			Class locatorClass = Class.forName(className);
			Locator locator = (Locator)locatorClass.newInstance();
			if (rootLocator != null) {
				for (String name: rootLocator.getProperties().keySet()) {
					locator.setProperty(name, VariableResolver.resolve(configuration, rootLocator.getProperties().get(name)));
				}
			}
			Map<String, String> properties = configuredLocator.getProperties();
			for (String name : properties.keySet()) {
				locator.setProperty(name, VariableResolver.resolve(configuration, properties.get(name)));
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
			ConfiguredComponent rootComponent = null;
			String className = configuredComponent.getClassName();
			if (className == null) {
				rootComponent = configuration.getComponent(configuredComponent.getName());
				className = rootComponent.getClassName();
			}
			Class componentClass = Class.forName(className);
			Component component = (Component)componentClass.newInstance();
			if (rootComponent != null) {
				for (String name: rootComponent.getProperties().keySet()) {
					component.setProperty(name, VariableResolver.resolve(configuration, rootComponent.getProperties().get(name)));
				}
			}
			Map<String, String> properties = configuredComponent.getProperties();
			for (String name : properties.keySet()) {
				component.setProperty(name, VariableResolver.resolve(configuration, properties.get(name)));
			}
			component.initialise(configuration);
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
