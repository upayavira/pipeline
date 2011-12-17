package org.odoko.pipeline.branchers;

import org.odoko.pipeline.pipelines.Consumer;

public interface Brancher extends Consumer {
	
	public void setQueue(String queue);
	public String getQueue();
}
