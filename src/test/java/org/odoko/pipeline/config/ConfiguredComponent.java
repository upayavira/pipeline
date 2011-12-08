package org.odoko.pipeline.config;

import java.util.HashMap;
import java.util.Map;

public class ConfiguredComponent {

	private String className;
	private String name;
	private Map<String, String> properties = new HashMap<String, String>();
	private String type;

	public ConfiguredComponent(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	public String getProperty(String name) {
		return this.properties.get(name);
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
}
