package org.odoko.pipeline.pipelines;

import org.odoko.pipeline.model.Asset;

public abstract class AbstractComponentTest {

	protected class MockConsumer extends AbstractConsumer {
		
		private Asset asset;
		
		public MockConsumer() {
		}
		
		public void consume(Asset asset) {
			this.asset = asset;
		}
		
		public Asset getAsset() {
			return asset;
		}
	}
}
