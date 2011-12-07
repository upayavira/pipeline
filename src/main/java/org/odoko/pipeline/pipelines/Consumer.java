package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public interface Consumer {
	
	public void consume(Asset asset);
}
