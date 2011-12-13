package org.odoko.pipeline.handlers;

import java.util.List;

import org.odoko.pipeline.model.Asset;

public interface AssetHandler {

	public void addAssets(String queue, List<Asset> assets);
	public void addAsset(String queue, Asset asset);
	public boolean hasNext(String queue);
	public Asset nextAsset(String queue);
	public List<String> getQueueNames();
}
