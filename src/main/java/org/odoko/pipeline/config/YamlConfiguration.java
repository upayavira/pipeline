package org.odoko.pipeline.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Consumer;
import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration extends AbstractConfiguration {

	@Override
	public void parse(String filename) throws IOException, ConfigurationException {
		Yaml yaml = new Yaml();
		HashMap<String,Object> result = (HashMap<String, Object>)yaml.load(new InputStreamReader(new FileInputStream(filename)));
		
		for (String key : result.keySet()) {
			Object value = result.get(key);
			
			if (key.startsWith("component.")) {
				parseComponent(key, value);
			} else if (key.startsWith("locator.")) {
				parseLocator(key, value);
			} else if (key.startsWith("pipeline.")) {
				parsePipeline(key, value);
			} else if (value instanceof String) {
				setProperty(key, (String)value);
			} else {
				throw new ConfigurationException("Invalid complex value for property " + key);
			}
		}
	}

	private void parseComponent(String key, Object value) throws ConfigurationException {
		key = key.substring("component.".length());
		ConfiguredComponent component = new ConfiguredComponent(key);
		if (value instanceof String) {
			component.setClassName((String)value);
		} else if (value instanceof HashMap) {
			HashMap map = (HashMap) value;
			for (String mapKey : (Set<String>)(map.keySet())) {
				Object mapValue = map.get(mapKey);
				if (mapValue instanceof String && mapKey.equals("class")) {
					component.setClassName((String)mapValue);
				} else if (mapValue instanceof String) {
					component.addProperty(mapKey, (String)mapValue);
				} else {
					throw new ConfigurationException("Unexpected property for component " + key + " called " + mapKey);
				}
			}
		}
		addComponent(key, component);
	}
	
	private void parseLocator(String key, Object value) throws ConfigurationException {
		key = key.substring("locator.".length());
		ConfiguredComponent component = new ConfiguredComponent(key);
		component.setType("locator");
		if (value instanceof String) {
			component.setClassName((String)value);
		} else if (value instanceof HashMap) {
			HashMap map = (HashMap) value;
			for (String mapKey : (Set<String>)(map.keySet())) {
				Object mapValue = map.get(mapKey);
				if (mapValue instanceof String && mapKey.equals("component")) {
					component.setName((String)mapValue);
				} else if (mapValue instanceof String) {
					component.addProperty(mapKey, (String)mapValue);
				} else {
					throw new ConfigurationException("Unexpected property for component " + key + " called " + mapKey);
				}
			}
		}
		addLocator(key, component);
	}

	private void parsePipeline(String key, Object value) throws ConfigurationException {
		key = key.substring("pipeline.".length());
		ConfiguredPipeline pipeline = new ConfiguredPipeline(key);
	    if (value instanceof ArrayList) {
	    	for (Object object : (List)value) {
	    		pipeline.addComponents(parsePipelineComponent(object));
	    	}
	    } else {
	    	throw new ConfigurationException("Unexpected child of " + key);
	    }
	    addPipeline(key, pipeline);
	}

	private ConfiguredComponent parsePipelineComponent(Object value) throws ConfigurationException {
		ConfiguredComponent component = new ConfiguredComponent();
		if (value instanceof String) {
			component.setName((String)value);
			return component;
		} else if (value instanceof HashMap) {
			HashMap map = (HashMap) value;
			for (String mapKey : (Set<String>)(map.keySet())) {
				Object mapValue = map.get(mapKey);
				if (mapValue == null) {
					component.setName(mapKey);
				} else if (mapValue instanceof String) {
					component.addProperty(mapKey, (String)mapValue);
				} else {
					throw new ConfigurationException("Unexpected property for component called " + mapKey + ": " + mapValue);
				}
			}
		} else {
			throw new ConfigurationException("Unexpected property: " + value);
		}
		return component;
	}
}
