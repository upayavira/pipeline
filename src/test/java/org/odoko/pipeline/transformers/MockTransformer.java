package org.odoko.pipeline.transformers;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractTransformer;
import org.odoko.pipeline.pipelines.Transformer;

public class MockTransformer extends AbstractTransformer implements Transformer {

	public MockTransformer() {
		setIncomingContentType("text/plain");
		setOutgoingContentType("text/plain");
	}
	
	@Override
	public void consume(Asset asset) {
		String content = (String)asset.getValue();
		content = content.toUpperCase();
		asset.setValue(content);
		next(asset);
	}
	
}