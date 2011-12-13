package org.odoko.pipeline.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.odoko.pipeline.model.Asset;

public class SimpleAssetHandler implements AssetHandler {

	private Map<String, Queue> queues = new HashMap<String, Queue>();

	private Queue getQueue(String queueName) {
		Queue queue = queues.get(queueName);
		if (queue == null) {
			queue = new Queue();
			queues.put(queueName, queue);
		}
		return queue;		
	}
	
	@Override
	public void addAssets(String queueName, List<Asset> assets) {
		Queue queue = getQueue(queueName);
		queue.addAll(assets);
	}

	@Override
	public List<String> getQueueNames() {
		return new ArrayList<String>(queues.keySet());
	}

	@Override
	public void addAsset(String queueName, Asset asset) {
		Queue queue = getQueue(queueName);
		queue.addAsset(asset);
	}

	@Override
	public boolean hasNext(String queue) {
		return getQueue(queue).hasNext();
	}
	
	@Override
	public Asset nextAsset(String queue) {
		return getQueue(queue).nextAsset();
	}
	
}
