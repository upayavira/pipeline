package org.odoko.pipeline.locators;

import java.util.List;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.Component;

public interface Locator extends Component {

	public List<Asset> locate();

}
