package org.odoko.pipeline.config;

import java.io.IOException;

import org.odoko.pipeline.pipelines.Component;
import org.odoko.pipeline.pipelines.Pipeline;

public interface Configuration {

	public void parse(String filename) throws IOException;
	public Pipeline getPipeline(String name);
	public void addPipeline(String name, Pipeline pipeline);
	public void addComponent(String name, Component component);
	
}
