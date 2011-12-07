package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

public class PipelineComponent {

	private Component component;
	private Map<String, String> properties = new HashMap<String, String>();
	public Component getComponent() {
		return component;
	}
	
	public void setComponent(Component component) {
		this.component = component;
	}
	
	public String getProperty(String name) {
		return properties.get(name);
	}
	
	public void setProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
}
