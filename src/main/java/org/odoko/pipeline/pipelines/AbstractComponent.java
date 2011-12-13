package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.config.Configuration;

public abstract class AbstractComponent implements Component {

	private Map<String, String>properties = new HashMap<String, String>();
	private Pipeline pipeline;
	
	@Override
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public String getProperty(String name) {
		return properties.get(name);
	}


	@Override
	public Pipeline getPipeline() {
		return this.pipeline;
	}

	@Override
	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	@Override
	public void initialise(Configuration config) {
		// do nothing
	}
}
