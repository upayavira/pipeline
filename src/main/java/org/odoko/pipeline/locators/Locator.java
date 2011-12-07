package org.odoko.pipeline.locators;

import java.util.List;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Producer;

public interface Locator extends Producer {

	public List<Asset> locate();
}
