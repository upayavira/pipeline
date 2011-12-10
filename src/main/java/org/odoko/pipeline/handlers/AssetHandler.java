package org.odoko.pipeline.handlers;

import java.util.List;

import org.odoko.pipeline.model.Asset;

public interface AssetHandler {

	public void addAssets(List<Asset> assets);
	public void addAsset(Asset asset);
	public boolean hasNext();
	public Asset nextAsset();
}
