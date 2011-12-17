package org.odoko.pipeline.branchers;

import org.odoko.pipeline.pipelines.AbstractConsumer;

public abstract class AbstractBrancher extends AbstractConsumer implements Brancher {

	private String queue;
	
	@Override
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	@Override
	public String getQueue() {
		return this.queue;
	}
}
