package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;

public interface Component {
	public Pipeline getPipeline();
	public void setPipeline(Pipeline pipeline);
	public String getLocation();
	public void setLocation(String location);
	public void initialise(Configuration config) throws ConfigurationException;
	public void fail(Asset asset, String message);
}
