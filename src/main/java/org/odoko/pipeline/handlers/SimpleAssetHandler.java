package org.odoko.pipeline.handlers;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;

public class SimpleAssetHandler implements AssetHandler {

	private List<Asset> assets = new ArrayList<Asset>();

	@Override
	public void addAssets(List<Asset> assets) {
		this.assets.addAll(assets);
	}

	@Override
	public void addAsset(Asset asset) {
		this.assets.add(asset);
	}

	@Override
	public boolean hasNext() {
		return this.assets.size() > 0;
	}
	
	@Override
	public Asset nextAsset() {
		return this.assets.remove(0);
	}
}
