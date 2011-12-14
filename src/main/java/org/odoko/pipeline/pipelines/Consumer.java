package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public interface Consumer extends Component {
	
	public void consumeBase(Asset asset);
	public void consume(Asset asset);
}
