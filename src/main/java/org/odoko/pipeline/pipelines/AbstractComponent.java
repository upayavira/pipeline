package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.model.Asset.AssetState;

public abstract class AbstractComponent implements Component {

	private Pipeline pipeline;
	private String location;

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
    	if (this.pipeline == null) {
    		return location;
    	} else {
    		return this.pipeline.getName() + ":" + this.location;
    	}
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
		asset.setState(AssetState.FAILED);
	}
	
	@Override
	public void succeed(Asset asset) {
		asset.setState(AssetState.SUCCESS);
	}
	
	protected boolean validate(Asset asset, Class clazz){
		return clazz.isInstance(asset.getValue());
	}
}
