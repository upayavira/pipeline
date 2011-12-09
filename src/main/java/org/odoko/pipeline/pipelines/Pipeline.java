package org.odoko.pipeline.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.collectors.Collector;
import org.odoko.pipeline.model.Asset;

public class Pipeline {

	private List<Component> components = new ArrayList<Component>();
	
	public List<Component> getComponents() {
		return components;
	}
	
	public void addComponent(Component component) {
		this.components.add(component);
	}
	
	/* Assumptions are made in this method about whether classes are consumers
	 * and/or producers. These will have already been validated by the 
	 * PipelineBuilder
	 */
	public void wire() {
		Component previousComponent = null;
		for (Component component: components) {
			if (previousComponent != null) {
				Producer producer = (Producer)previousComponent;
				Consumer consumer = (Consumer)component;
  			    producer.wire(consumer);
			}
			previousComponent = component;
		}
	}
	
	public void process(Asset asset) {
		Collector collector = (Collector)components.get(0);
		collector.process(asset);
	}
}
