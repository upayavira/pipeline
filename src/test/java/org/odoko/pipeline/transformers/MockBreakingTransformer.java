package org.odoko.pipeline.transformers;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.model.Asset.AssetState;
import org.odoko.pipeline.transformers.AbstractTransformer;
import org.odoko.pipeline.pipelines.PipelineException;

/**
 * This mock transformer is designed to throw an exception, 
 * allowing for testing of the error handling code in a pipeline.
 */

public class MockBreakingTransformer extends AbstractTransformer {

	public MockBreakingTransformer() {
		setIncomingClass(String.class);
		setOutgoingClass(String.class);
	}
	
	@Override
	public void consume(Asset asset) throws PipelineException {
		throw new PipelineException(asset, AssetState.FAILED, getLocation(), "TRANSFORM FAILED");
	}
	
}
