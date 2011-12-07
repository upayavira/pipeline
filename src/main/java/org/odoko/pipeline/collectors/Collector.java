package org.odoko.pipeline.collectors;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Producer;

public interface Collector extends Producer {

	public void process(Asset asset);
}
