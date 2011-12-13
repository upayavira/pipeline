package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.config.Configuration;

public interface Component {
	public void setProperty(String name, String value);
	public String getProperty(String string);
	public Pipeline getPipeline();
	public void setPipeline(Pipeline pipeline);
	public void initialise(Configuration config);
}
