package org.odoko.pipeline.config;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredPipeline {

	private String name;
	private List<ConfiguredComponent> components = new ArrayList<ConfiguredComponent>();
	
	public ConfiguredPipeline(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ConfiguredComponent> getComponents() {
		return components;
	}
	public void addComponents(ConfiguredComponent component) {
		this.components.add(component);
	}
}
