package org.odoko.pipeline.handlers;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;

public class Queue {

	private List<Asset> assets = new ArrayList<Asset>();

	public void addAsset(Asset asset) {
		this.assets.add(asset);
	}
	
	public boolean hasNext() {
		return assets.size() > 0;
	}

	public Asset nextAsset() {
		return assets.remove(0);
	}

	public void addAll(List<Asset> assets) {
		this.assets.addAll(assets);
	}
}
