package org.odoko.pipeline.transformers;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.transformers.AbstractTransformer;

public class MockTransformer extends AbstractTransformer {

	public MockTransformer() {
		setIncomingClass(String.class);
		setOutgoingClass(String.class);
	}
	
	@Override
	public void consume(Asset asset) {
		String content = (String)asset.getValue();
		content = content.toUpperCase();
		asset.setValue(content);
		next(asset);
	}
	
}
