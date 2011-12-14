package org.odoko.pipeline.pipelines;

import java.util.HashMap;
import java.util.Map;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;

public abstract class AbstractComponent implements Component {

	private Map<String, String>properties = new HashMap<String, String>();
	private Pipeline pipeline;
	private String location;
	
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
	public String getLocation() {
		return this.pipeline.getName() + ":" + location;
	}

    @Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void initialise(Configuration config) throws ConfigurationException {
		// do nothing
	}
	
	@Override
	public void fail(Asset asset, String message) {
		
	}
	
	protected boolean validate(Asset asset, Class clazz){
		return asset.getValue().getClass().isAssignableFrom(clazz);
	}

}
