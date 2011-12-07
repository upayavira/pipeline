package org.odoko.pipeline.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.collectors.Collector;
import org.odoko.pipeline.dispatchers.Dispatcher;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.model.Asset;

public class Pipeline {

	private Locator locator;
	private Collector collector;
	private List<Transformer> transformers = new ArrayList<Transformer>();
	private Dispatcher dispatcher;
	public Locator getLocator() {
		return locator;
	}
	
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	
	public Collector getCollector() {
		return collector;
	}
	
	public void setCollector(Collector collector) {
		this.collector = collector;
	}
	
	public List<Transformer> getTransformers() {
		return transformers;
	}
	
	public void addTransformer(Transformer transformer) {
		this.transformers.add(transformer);
	}
	
	public Dispatcher getDispatcher() {
		return dispatcher;
	}
	
	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public void wire() {
		Producer previousComponent = this.collector;
		for (Transformer transformer : transformers) {
			previousComponent.wire(transformer);
			previousComponent = transformer;
		}
		previousComponent.wire(dispatcher);
	}
	
	public void process(Asset asset) {
		this.collector.process(asset);
	}
}
