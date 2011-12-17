package org.odoko.pipeline.pipelines;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.ConfiguredComponent;
import org.odoko.pipeline.config.ConfiguredPipeline;
import org.odoko.pipeline.config.VariableResolver;
import org.odoko.pipeline.locators.Locator;

public class PipelineBuilder {

	public Pipeline build(Configuration configuration, ConfiguredPipeline configuredPipeline) throws ConfigurationException {
		Pipeline pipeline = new Pipeline();
		pipeline.setName(configuredPipeline.getName());
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
				rootLocator = configuration.getComponent(configuredLocator.getName());
				className = rootLocator.getClassName();
			}
			Class locatorClass = Class.forName(className);
			Locator locator = (Locator)locatorClass.newInstance();
			if (rootLocator != null) {
				for (String name: rootLocator.getProperties().keySet()) {
					setProperty(locator, name, VariableResolver.resolve(configuration, rootLocator.getProperties().get(name)));
				}
			}
			Map<String, String> properties = configuredLocator.getProperties();
			for (String name : properties.keySet()) {
				setProperty(locator, name, VariableResolver.resolve(configuration, properties.get(name)));
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
					setProperty(component, name, VariableResolver.resolve(configuration, rootComponent.getProperties().get(name)));
				}
			}
			Map<String, String> properties = configuredComponent.getProperties();
			for (String name : properties.keySet()) {
				setProperty(component, name, VariableResolver.resolve(configuration, properties.get(name)));
			}
			component.initialise(configuration);
			component.setLocation(configuredComponent.getName());
			return component;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		} catch (IllegalAccessException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		} catch (InstantiationException e) {
			throw new ConfigurationException("Cannot instantiate " + configuredComponent.getClassName());
		}
	}
	
	private void setProperty(Component component, String name, Object value) throws ConfigurationException {
		try {
			String propertyName = getPropertyName(name);
			PropertyUtils.setSimpleProperty(component, propertyName, value);
		} catch (IllegalAccessException e) {
			throw new ConfigurationException("No such property '" + name + "' for " + component.getLocation());
		} catch (InvocationTargetException e) {
			throw new ConfigurationException("No such property '" + name + "' for " + component.getLocation());
		} catch (NoSuchMethodException e) {
			throw new ConfigurationException("No such property '" + name + "' for " + component.getLocation());
		}
	}
	
	private String getPropertyName(String name) {
		StringBuffer buffer = new StringBuffer();
		for (String part : StringUtils.split(name, "-")) {
			buffer.append(StringUtils.capitalize(part));
		}
		String prop = buffer.toString();
		return prop.substring(0,1).toLowerCase() + prop.substring(1);
	}
}
