package org.odoko.pipeline.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;

public class PipelineException extends RuntimeException {

	private Asset asset;
	private Asset.AssetState state;
	private List<String> stacktrace = new ArrayList<String>();
	
	public PipelineException(Asset asset, Asset.AssetState state, String location, String message) {
		super(message);
		this.asset = asset;
		this.state = state;
		this.stacktrace.add(location);
	}

	public void addLocation(String location) {
		this.stacktrace.add(location);
	}
	
	public List<String> getPipelineStackTrace() {
		return stacktrace;
	}
}
