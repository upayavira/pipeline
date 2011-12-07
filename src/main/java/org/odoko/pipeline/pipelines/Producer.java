package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public interface Producer {

	public void wire(Consumer consumer);
	
	public void process(Asset asset);

	void next(Asset asset);
	
}
