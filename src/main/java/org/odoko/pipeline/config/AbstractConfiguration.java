package org.odoko.pipeline.config;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.pipelines.Component;
import org.odoko.pipeline.pipelines.Pipeline;

public abstract class AbstractConfiguration implements Configuration {

	private Map<String, Pipeline> pipelines = new HashMap<String, Pipeline>();
	
	@Override
	public Pipeline getPipeline(String name) {
		return pipelines.get(name);
	}

	@Override
	public void addPipeline(String name, Pipeline pipeline) {
		this.pipelines.put(name, pipeline);
	}

	@Override
	public void addComponent(String name, Component component) {
		// TODO Auto-generated method stub

	}

}
