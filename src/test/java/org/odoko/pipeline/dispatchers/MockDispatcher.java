package org.odoko.pipeline.dispatchers;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractConsumer;

public class MockDispatcher extends AbstractConsumer implements Dispatcher {

	private List<Asset> assets = new ArrayList<Asset>();
	
	public MockDispatcher() {
		setIncomingClass(String.class);
	}

	@Override
	public void consume(Asset asset) {
		assets.add(asset);
	}

	public List<Asset> getDispatchedAssets() {
	    return assets;	
	}
	

}
