package org.odoko.pipeline.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.collectors.Collector;
import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.model.Asset;

public class Pipeline {

	private List<Component> components = new ArrayList<Component>();
	private AssetHandler handler;
	
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
			component.setPipeline(this);
			if (previousComponent != null) {
				Producer producer = (Producer)previousComponent;
				Consumer consumer = (Consumer)component;
  			    producer.wire(consumer);
			}
			previousComponent = component;
		}
	}

	public void process(Asset asset) {
		Component component = components.get(0);
		if (component instanceof Collector) {
			// initial pipeline
			Collector collector = (Collector)components.get(0);
			collector.process(asset);			
		} else {
			// sub-pipeline
			Consumer consumer = (Consumer)components.get(0);
			consumer.consume(asset);
		}
	}

	public void setHandler(AssetHandler handler) {
		this.handler = handler;
	}
	
	public AssetHandler getHandler() {
		return handler;
	}
}
